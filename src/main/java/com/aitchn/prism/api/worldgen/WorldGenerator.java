package com.aitchn.prism.api.worldgen;

import com.aitchn.prism.api.behavior.BehaviorOptions;

@FunctionalInterface
public interface WorldGenerator {
    WorldGenerationResult generate(WorldGenerationContext context, BehaviorOptions options);
}
