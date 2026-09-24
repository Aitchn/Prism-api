package com.aitchn.prism.api.behavior.item;

/** Arrow damage is a projectile contribution, not an additional complete weapon damage value. */
public record RangedAmmunitionProfile(double damage, int penetration, double spread, double gravity,
                                      double velocityRetention, double speedMultiplier,
                                      double recoveryChance, boolean fireproof) {
    public RangedAmmunitionProfile {
        if (!Double.isFinite(damage) || damage < 0 || damage > 100 || penetration < 0 || penetration > 127
                || !Double.isFinite(spread) || spread < 0 || spread > 20
                || !Double.isFinite(gravity) || gravity < 0 || gravity > 1
                || !Double.isFinite(velocityRetention) || velocityRetention < 0.01 || velocityRetention > 1
                || !Double.isFinite(speedMultiplier) || speedMultiplier < 0.05 || speedMultiplier > 10
                || !Double.isFinite(recoveryChance) || recoveryChance < 0 || recoveryChance > 1) {
            throw new IllegalArgumentException("Invalid ranged ammunition profile");
        }
    }
}
