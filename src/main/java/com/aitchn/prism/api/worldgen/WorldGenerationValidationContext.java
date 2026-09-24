package com.aitchn.prism.api.worldgen;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Set;

public record WorldGenerationValidationContext(
        PrismKey definitionId,
        BehaviorOptions options,
        Set<PrismKey> blocks,
        Set<PrismKey> treeFeatures
) {
    public WorldGenerationValidationContext {
        blocks = Set.copyOf(blocks);
        treeFeatures = Set.copyOf(treeFeatures);
    }
}
