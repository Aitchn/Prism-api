package com.aitchn.prism.api.combat;

/** Logical health units. Absorption is separate from health and may exceed maximum health. */
public record HealthSnapshot(double current, double maximum, double absorption) {
    public HealthSnapshot {
        if (!Double.isFinite(current) || !Double.isFinite(maximum) || !Double.isFinite(absorption)
                || maximum <= 0 || current < 0 || current > maximum || absorption < 0) {
            throw new IllegalArgumentException("Invalid health snapshot");
        }
    }

    public double fraction() { return current / maximum; }
}
