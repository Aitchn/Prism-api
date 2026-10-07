package com.aitchn.prism.api.progress;

import com.aitchn.prism.api.PrismKey;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import org.bukkit.plugin.Plugin;

public interface ProgressService {
    CompletionStage<PlayerProgressView> load(UUID playerId);

    PlayerProgressView cached(UUID playerId);

    CompletionStage<ResearchResult> completeResearch(UUID playerId, PrismKey research);

    /** Context-aware mutation. Older providers of this service explicitly reject this new contract. */
    default CompletionStage<ResearchResult> completeResearch(UUID playerId, PrismKey research,
            com.aitchn.prism.api.protection.ProtectionContext context) {
        return java.util.concurrent.CompletableFuture.failedFuture(
                new UnsupportedOperationException("This ProgressService does not support action context"));
    }

    CompletionStage<PlayerProgressView> revokeResearch(UUID playerId, PrismKey research);

    /** Context-aware mutation. Older providers of this service explicitly reject this new contract. */
    default CompletionStage<PlayerProgressView> revokeResearch(UUID playerId, PrismKey research,
            com.aitchn.prism.api.protection.ProtectionContext context) {
        return java.util.concurrent.CompletableFuture.failedFuture(
                new UnsupportedOperationException("This ProgressService does not support action context"));
    }

    CompletionStage<PlayerProgressView> incrementCounter(UUID playerId, PrismKey counter, long amount);

    /** Context-aware mutation. Older providers of this service explicitly reject this new contract. */
    default CompletionStage<PlayerProgressView> incrementCounter(UUID playerId, PrismKey counter, long amount,
            com.aitchn.prism.api.protection.ProtectionContext context) {
        return java.util.concurrent.CompletableFuture.failedFuture(
                new UnsupportedOperationException("This ProgressService does not support action context"));
    }

    void subscribe(Plugin owner, ProgressListener listener);

    void unsubscribe(Plugin owner);
}
