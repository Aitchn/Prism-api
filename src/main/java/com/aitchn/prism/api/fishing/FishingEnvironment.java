package com.aitchn.prism.api.fishing;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/** Immutable hook-local facts captured on the owning region at catch selection. */
public record FishingEnvironment(PrismKey biome, long dayTime, Weather weather, boolean underground) {
    public enum Weather { CLEAR, PRECIPITATION, THUNDER, UNKNOWN }

    public FishingEnvironment {
        Objects.requireNonNull(biome, "biome");
        Objects.requireNonNull(weather, "weather");
        if (dayTime < -1 || dayTime >= 24_000) {
            throw new IllegalArgumentException("Fishing day time must be -1 (unknown) or in [0, 24000)");
        }
    }

    /** Legacy callers have no hook facts; restricted species cannot be selected without them. */
    public static FishingEnvironment unknown(PrismKey biome) {
        return new FishingEnvironment(biome, -1, Weather.UNKNOWN, false);
    }
}
