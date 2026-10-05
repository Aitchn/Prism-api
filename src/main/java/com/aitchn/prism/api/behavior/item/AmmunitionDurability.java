package com.aitchn.prism.api.behavior.item;

/** Opt-in reusable ammunition. Native damage counts spent uses; spending the last use retains an empty carrier. */
public record AmmunitionDurability(int capacity, double infinityConsumeChance) {
    public AmmunitionDurability {
        if (capacity < 1 || capacity > 1_000_000 || !Double.isFinite(infinityConsumeChance)
                || infinityConsumeChance < 0 || infinityConsumeChance > 1) {
            throw new IllegalArgumentException("Invalid ammunition durability policy");
        }
    }

    public boolean consumeWithInfinity(double roll) {
        if (!Double.isFinite(roll) || roll < 0 || roll >= 1) {
            throw new IllegalArgumentException("Roll must be in [0, 1)");
        }
        return roll < infinityConsumeChance;
    }
}
