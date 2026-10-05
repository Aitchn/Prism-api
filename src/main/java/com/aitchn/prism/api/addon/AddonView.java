package com.aitchn.prism.api.addon;

import java.util.Map;

/** Immutable status; registeredTypes counts catalog registrations, not content definitions. */
public record AddonView(String id, String name, String version, AddonDescriptor descriptor,
                        AddonStatus status, boolean enabled, String reason,
                        Map<String, Integer> registeredTypes, Map<String, String> skippedContent) {
    public AddonView {
        registeredTypes = Map.copyOf(registeredTypes);
        skippedContent = Map.copyOf(skippedContent);
    }
}
