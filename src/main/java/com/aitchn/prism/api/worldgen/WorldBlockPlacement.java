package com.aitchn.prism.api.worldgen;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;
import java.util.Objects;

public record WorldBlockPlacement(
        int x,
        int y,
        int z,
        PrismKey block,
        WorldBlockAxis axis,
        Map<String, String> data
) {
    public WorldBlockPlacement {
        if (x < 0 || x > 15 || z < 0 || z > 15) {
            throw new IllegalArgumentException("World generation placements must remain inside the target chunk");
        }
        Objects.requireNonNull(block, "block");
        Objects.requireNonNull(axis, "axis");
        data = Map.copyOf(data);
    }

    public WorldBlockPlacement(int x, int y, int z, PrismKey block) {
        this(x, y, z, block, WorldBlockAxis.NONE, Map.of());
    }
}
