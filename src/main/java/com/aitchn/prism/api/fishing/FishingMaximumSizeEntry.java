package com.aitchn.prism.api.fishing;

import java.util.Objects;
import java.util.UUID;

/** One row of the maximum-size leaderboard; players tied on size share the same competition rank. */
public record FishingMaximumSizeEntry(int rank, UUID playerId, String playerName, FishingCatch maximum) {
    public FishingMaximumSizeEntry {
        if (rank <= 0) {
            throw new IllegalArgumentException("rank must be positive");
        }
        playerId = Objects.requireNonNull(playerId, "playerId");
        playerName = Objects.requireNonNull(playerName, "playerName");
        maximum = Objects.requireNonNull(maximum, "maximum");
    }
}
