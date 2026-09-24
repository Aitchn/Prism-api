package com.aitchn.prism.api.pack;

import java.util.Map;
import java.util.Set;

public record ContentPackDescriptor(
        int schemaVersion,
        String id,
        String version,
        Map<String, String> dependencies,
        Map<String, String> optionalDependencies,
        Set<String> capabilities,
        boolean legacy
) {
    public ContentPackDescriptor {
        dependencies = Map.copyOf(dependencies);
        optionalDependencies = Map.copyOf(optionalDependencies);
        capabilities = Set.copyOf(capabilities);
    }

    public static ContentPackDescriptor legacy(String id) {
        return new ContentPackDescriptor(0, id, "0.0.0", Map.of(), Map.of(), Set.of(), true);
    }
}
