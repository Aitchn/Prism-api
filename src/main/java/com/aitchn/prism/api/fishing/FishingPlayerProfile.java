package com.aitchn.prism.api.fishing;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/** Immutable snapshot of all fishing records owned by one player. */
public record FishingPlayerProfile(UUID playerId, long revision, Map<PrismKey, FishingPlayerStats> fish) {
    public FishingPlayerProfile {
        playerId = Objects.requireNonNull(playerId, "playerId");
        if (revision < 0) {
            throw new IllegalArgumentException("Fishing profile revision cannot be negative");
        }
        fish = Map.copyOf(Objects.requireNonNull(fish, "fish"));
    }

    public static FishingPlayerProfile empty(UUID playerId) {
        return new FishingPlayerProfile(playerId, 0, Map.of());
    }

    public FishingPlayerStats stats(PrismKey species) {
        return fish.getOrDefault(Objects.requireNonNull(species, "species"), FishingPlayerStats.empty());
    }

    public Optional<FishingPlayerStats> discovered(PrismKey species) {
        FishingPlayerStats stats = stats(species);
        return stats.discovered() ? Optional.of(stats) : Optional.empty();
    }
}
