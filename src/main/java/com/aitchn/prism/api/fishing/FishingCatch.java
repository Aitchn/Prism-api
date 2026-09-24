package com.aitchn.prism.api.fishing;

import java.time.Instant;
import java.util.Objects;

/** One measured catch, retained with the exact capture instant used by records and specimens. */
public record FishingCatch(double sizeCm, Instant caughtAt) {
    public FishingCatch {
        if (!Double.isFinite(sizeCm) || sizeCm < 0.1 || sizeCm > 100_000.0) {
            throw new IllegalArgumentException("Fishing catch size must be finite and in [0.1, 100000]");
        }
        double rounded = Math.rint(sizeCm * 10.0) / 10.0;
        if (Math.abs(sizeCm - rounded) > 1.0e-7) {
            throw new IllegalArgumentException("Fishing catch size must have at most one decimal place");
        }
        caughtAt = Objects.requireNonNull(caughtAt, "caughtAt");
    }
}
