package com.aitchn.prism.api.progress;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public record PlayerProgressView(
        UUID playerId,
        long revision,
        Set<PrismKey> completedResearch,
        Map<PrismKey, Long> counters
) {
    public PlayerProgressView {
        completedResearch = Set.copyOf(completedResearch);
        counters = Map.copyOf(counters);
    }
}
