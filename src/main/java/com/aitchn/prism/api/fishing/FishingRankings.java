package com.aitchn.prism.api.fishing;

/** Competition ranks for a discovered species; zero means the player has no rank yet. */
public record FishingRankings(int catchCountRank, int maximumSizeRank) {
    public FishingRankings {
        if (catchCountRank < 0 || maximumSizeRank < 0) {
            throw new IllegalArgumentException("Fishing ranks cannot be negative");
        }
    }

    public static FishingRankings undiscovered() {
        return new FishingRankings(0, 0);
    }
}
