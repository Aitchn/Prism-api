package com.aitchn.prism.api.input;

import java.util.Set;

/** Compatibility is source availability, not a guarantee that a physical gesture emits a packet. */
public record InputCapability(boolean supported, Set<InputPhase> phases, boolean nativeCancellation,
                              String limitation) {
    public InputCapability { phases = Set.copyOf(phases); }
}
