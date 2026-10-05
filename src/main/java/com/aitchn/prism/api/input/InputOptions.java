package com.aitchn.prism.api.input;

import java.util.EnumSet;
import java.util.Set;

/** Ordered observation only. No hidden inventory, game-mode, movement or camera policy. */
public record InputOptions(Set<InputSource> sources, boolean holds) {
    public InputOptions {
        sources = Set.copyOf(sources);
        if (sources.isEmpty()) throw new IllegalArgumentException("Select at least one source");
    }
    public static InputOptions all() { return new InputOptions(EnumSet.allOf(InputSource.class), false); }
    public static InputOptions of(InputSource first, InputSource... rest) {
        return new InputOptions(EnumSet.of(first, rest), false);
    }
    public InputOptions withHolds() { return new InputOptions(sources, true); }
}
