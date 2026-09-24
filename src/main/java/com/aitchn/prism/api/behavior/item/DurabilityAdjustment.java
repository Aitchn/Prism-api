package com.aitchn.prism.api.behavior.item;

public record DurabilityAdjustment(int damage) {
    public DurabilityAdjustment {
        if (damage < 0) {
            throw new IllegalArgumentException("Durability damage cannot be negative");
        }
    }

    public static DurabilityAdjustment unchanged(int damage) {
        return new DurabilityAdjustment(damage);
    }
}
