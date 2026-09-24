package com.aitchn.prism.api.behavior.item;

public record DamageAdjustment(double multiplier, double additive) {
    private static final DamageAdjustment IDENTITY = new DamageAdjustment(1.0D, 0.0D);

    public DamageAdjustment {
        if (!Double.isFinite(multiplier) || multiplier < 0.0D) {
            throw new IllegalArgumentException("Damage multiplier must be finite and non-negative");
        }
        if (!Double.isFinite(additive)) {
            throw new IllegalArgumentException("Damage additive must be finite");
        }
    }

    public static DamageAdjustment identity() {
        return IDENTITY;
    }

    public double apply(double damage) {
        if (!Double.isFinite(damage) || damage < 0.0D) {
            throw new IllegalArgumentException("Damage must be finite and non-negative");
        }
        return Math.max(0.0D, damage * multiplier + additive);
    }
}
