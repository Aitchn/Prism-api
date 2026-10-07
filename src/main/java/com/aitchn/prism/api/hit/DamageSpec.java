package com.aitchn.prism.api.hit;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/** Logical BASE damage before a single part multiplier, native shielding and subsequent defenses. */
public record DamageSpec(PrismKey type, double amount, double pressure) {
    public DamageSpec {
        Objects.requireNonNull(type, "type");
        if (!Double.isFinite(amount) || amount < 0 || amount > 1_000_000
                || !Double.isFinite(pressure) || pressure <= 0 || pressure > 2) throw new IllegalArgumentException("Invalid damage specification");
    }
}
