package com.aitchn.prism.api.addon;

import java.util.Set;

/** Explicit addon identity and compatibility, independent of content count and Bukkit enable state. */
public record AddonDescriptor(String id, int apiMajor, int minimumMinor, int maximumMinor,
                              String buildApi, Set<String> namespaces) {
    public AddonDescriptor {
        if (id == null || !id.matches("[a-z0-9._-]{1,64}")) {
            throw new IllegalArgumentException("Invalid addon id");
        }
        if (apiMajor < 0 || minimumMinor < 0 || maximumMinor < minimumMinor) {
            throw new IllegalArgumentException("Invalid inclusive API range");
        }
        if (buildApi != null && !buildApi.matches("[0-9]+\\.[0-9]+")) {
            throw new IllegalArgumentException("Build API must be explicit major.minor or null");
        }
        namespaces = Set.copyOf(namespaces);
        if (namespaces.stream().anyMatch(value -> !value.matches("[a-z0-9._-]{1,64}") || value.equals("prism"))) {
            throw new IllegalArgumentException("Invalid or reserved addon namespace");
        }
    }

    public boolean supports(int major, int minor) {
        return major == apiMajor && minor >= minimumMinor && minor <= maximumMinor;
    }

    public String compatibility() {
        return apiMajor + "." + minimumMinor + " - " + apiMajor + "." + maximumMinor;
    }
}
