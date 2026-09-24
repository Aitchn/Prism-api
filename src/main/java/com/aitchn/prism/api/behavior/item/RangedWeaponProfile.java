package com.aitchn.prism.api.behavior.item;

/** Values before native enchantments. A bow releases a charge; a crossbow retains one loaded round. */
public record RangedWeaponProfile(Mode mode, int drawTicks, double speed, double spread,
                                  int durabilityPerShot, double reboundTimeMultiplier) {
    public RangedWeaponProfile {
        java.util.Objects.requireNonNull(mode, "mode");
        if (drawTicks < 1 || drawTicks > 1200 || !Double.isFinite(speed) || speed <= 0 || speed > 10
                || !Double.isFinite(spread) || spread < 0 || spread > 20
                || durabilityPerShot < 0 || durabilityPerShot > 100
                || !Double.isFinite(reboundTimeMultiplier) || reboundTimeMultiplier < 0.1 || reboundTimeMultiplier > 1) {
            throw new IllegalArgumentException("Invalid ranged weapon profile");
        }
    }

    public enum Mode { BOW, CROSSBOW }

    public double charge(int ticks) {
        double fraction = Math.clamp((double) ticks / drawTicks, 0, 1);
        return mode == Mode.CROSSBOW ? (fraction >= 1 ? 1 : 0) : (fraction * fraction + 2 * fraction) / 3;
    }
}
