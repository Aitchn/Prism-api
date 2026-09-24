package com.aitchn.prism.api.item.guide;

import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

/** Normalized, immutable effective values used by item information providers. */
public record ItemGuideFacts(
        @Nullable Integer harvestLevel,
        @Nullable Integer maxDamage,
        @Nullable Integer enchantability,
        List<MiningSpeed> miningSpeeds,
        @Nullable Double attackDamage,
        @Nullable Double attackSpeed,
        @Nullable Double attackKnockback,
        @Nullable Double armor,
        @Nullable Double armorToughness,
        @Nullable Double movementSpeedPercent,
        @Nullable Double knockbackResistancePercent,
        @Nullable Double repairPercent
) {
    public ItemGuideFacts {
        miningSpeeds = List.copyOf(Objects.requireNonNull(miningSpeeds, "miningSpeeds"));
    }

    public record MiningSpeed(String blockTag, double speed) {
        public MiningSpeed {
            if (blockTag == null || blockTag.isBlank() || !blockTag.contains(":")) {
                throw new IllegalArgumentException("Mining speed block tags must be namespaced strings");
            }
            if (!Double.isFinite(speed) || speed < 0.0D) {
                throw new IllegalArgumentException("Mining speed must be finite and non-negative");
            }
        }
    }
}
