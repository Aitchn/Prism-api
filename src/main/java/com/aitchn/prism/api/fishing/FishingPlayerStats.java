package com.aitchn.prism.api.fishing;

import java.util.Objects;
import java.util.Optional;

/** Per-player, per-species counters and records. The specimen window is cleared after successful crafting. */
public record FishingPlayerStats(
        long catchCount,
        Optional<FishingCatch> historicalMaximum,
        Optional<FishingCatch> specimenWindowMaximum
) {
    public FishingPlayerStats {
        if (catchCount < 0) {
            throw new IllegalArgumentException("Fishing catch count cannot be negative");
        }
        historicalMaximum = Objects.requireNonNull(historicalMaximum, "historicalMaximum");
        specimenWindowMaximum = Objects.requireNonNull(specimenWindowMaximum, "specimenWindowMaximum");
        if (catchCount == 0 && (historicalMaximum.isPresent() || specimenWindowMaximum.isPresent())) {
            throw new IllegalArgumentException("Undiscovered fish cannot have catch records");
        }
        if (catchCount > 0 && historicalMaximum.isEmpty()) {
            throw new IllegalArgumentException("Discovered fish must have a historical maximum");
        }
    }

    public static FishingPlayerStats empty() {
        return new FishingPlayerStats(0, Optional.empty(), Optional.empty());
    }

    public boolean discovered() {
        return catchCount > 0;
    }
}
