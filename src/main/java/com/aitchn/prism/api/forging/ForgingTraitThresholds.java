package com.aitchn.prism.api.forging;

/** Accepted range for tier values configured under a product's {@code traits.thresholds}. */
public record ForgingTraitThresholds(double minimum, double maximum, boolean integral) {
    public ForgingTraitThresholds {
        if (!Double.isFinite(minimum) || !Double.isFinite(maximum) || minimum < 0 || minimum > maximum) {
            throw new IllegalArgumentException("Trait threshold bounds must be finite, non-negative and ordered");
        }
    }

    public void validate(double value) {
        if (!Double.isFinite(value) || value <= 0 || value < minimum || value > maximum
                || integral && value != Math.rint(value)) {
            throw new IllegalArgumentException("Trait tier value must be positive and from " + minimum + " through " + maximum);
        }
    }
}
