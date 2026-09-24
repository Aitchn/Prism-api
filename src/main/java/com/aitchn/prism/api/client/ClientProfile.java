package com.aitchn.prism.api.client;

import java.util.Locale;
import java.util.Set;

public record ClientProfile(ClientPlatform platform, Locale locale, Set<ClientCapability> capabilities) {
    public ClientProfile {
        capabilities = Set.copyOf(capabilities);
    }

    public boolean supports(ClientCapability capability) {
        return capabilities.contains(capability);
    }
}
