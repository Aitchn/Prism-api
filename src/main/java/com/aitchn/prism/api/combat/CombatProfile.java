package com.aitchn.prism.api.combat;

/** Fixed encounter references; never derive these from the entering player's equipment. */
public record CombatProfile(double armorReference, double toughnessReference) {
    public CombatProfile {
        positive(armorReference, "armorReference");
        positive(toughnessReference, "toughnessReference");
    }

    private static void positive(double value, String name) {
        if (!Double.isFinite(value) || value <= 0 || value > 1_000_000) {
            throw new IllegalArgumentException(name + " must be finite and in (0, 1000000]");
        }
    }
}
