package com.aitchn.prism.api.worldgen;

import java.util.List;

public record WorldGenerationResult(List<WorldBlockPlacement> placements) {
    private static final WorldGenerationResult EMPTY = new WorldGenerationResult(List.of());

    public WorldGenerationResult {
        placements = List.copyOf(placements);
    }

    public static WorldGenerationResult empty() {
        return EMPTY;
    }
}
