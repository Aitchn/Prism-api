package com.aitchn.prism.api.fishing;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;
import java.util.Optional;

/** The all-time server record for one species. Offline players remain represented in the ranking index. */
public record FishingServerRecord(PrismKey species, Optional<FishingCatch> historicalMaximum) {
    public FishingServerRecord {
        species = Objects.requireNonNull(species, "species");
        historicalMaximum = Objects.requireNonNull(historicalMaximum, "historicalMaximum");
    }

    public static FishingServerRecord empty(PrismKey species) {
        return new FishingServerRecord(species, Optional.empty());
    }
}
