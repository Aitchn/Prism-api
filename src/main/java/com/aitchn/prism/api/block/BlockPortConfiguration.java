package com.aitchn.prism.api.block;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/** Captured port configuration. Omitted sides are explicitly disabled. No mutable machine state escapes. */
public record BlockPortConfiguration(String name, BlockPortKind kind, BlockPortMode capability,
                                     Map<BlockSide, BlockPortMode> sides) {
    public BlockPortConfiguration {
        if (name == null || !name.matches("[a-z0-9._-]+")) {
            throw new IllegalArgumentException("Invalid port name: " + name);
        }
        Objects.requireNonNull(kind, "kind");
        Objects.requireNonNull(capability, "capability");
        EnumMap<BlockSide, BlockPortMode> copy = new EnumMap<>(BlockSide.class);
        for (BlockSide side : BlockSide.values()) copy.put(side, BlockPortMode.NONE);
        sides.forEach((side, mode) -> {
            Objects.requireNonNull(side, "side");
            if (!capability.permits(Objects.requireNonNull(mode, "mode"))) {
                throw new IllegalArgumentException("Port side exceeds its buffer capability: " + name);
            }
            copy.put(side, mode);
        });
        sides = Collections.unmodifiableMap(copy);
    }

    public BlockPortMode mode(BlockDirection worldFace, BlockOrientation orientation) {
        return sides.get(orientation.local(worldFace));
    }

    public BlockPortConfiguration with(BlockSide side, BlockPortMode mode) {
        EnumMap<BlockSide, BlockPortMode> copy = new EnumMap<>(sides);
        copy.put(Objects.requireNonNull(side, "side"), Objects.requireNonNull(mode, "mode"));
        return new BlockPortConfiguration(name, kind, capability, copy);
    }
}
