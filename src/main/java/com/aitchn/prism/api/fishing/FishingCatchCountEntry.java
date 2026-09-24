package com.aitchn.prism.api.fishing;

import java.util.Objects;
import java.util.UUID;

/** One row of the catch-count leaderboard; players tied on count share the same competition rank. */
public record FishingCatchCountEntry(int rank, UUID playerId, String playerName, long catchCount) {
    public FishingCatchCountEntry {
        if (rank <= 0) {
            throw new IllegalArgumentException("rank must be positive");
        }
        playerId = Objects.requireNonNull(playerId, "playerId");
        playerName = Objects.requireNonNull(playerName, "playerName");
        if (catchCount <= 0) {
            throw new IllegalArgumentException("catchCount must be positive");
        }
    }
}
