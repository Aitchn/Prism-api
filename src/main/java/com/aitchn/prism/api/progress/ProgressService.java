package com.aitchn.prism.api.progress;

import com.aitchn.prism.api.PrismKey;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import org.bukkit.plugin.Plugin;

public interface ProgressService {
    CompletionStage<PlayerProgressView> load(UUID playerId);

    PlayerProgressView cached(UUID playerId);

    CompletionStage<ResearchResult> completeResearch(UUID playerId, PrismKey research);

    CompletionStage<PlayerProgressView> revokeResearch(UUID playerId, PrismKey research);

    CompletionStage<PlayerProgressView> incrementCounter(UUID playerId, PrismKey counter, long amount);

    void subscribe(Plugin owner, ProgressListener listener);

    void unsubscribe(Plugin owner);
}
