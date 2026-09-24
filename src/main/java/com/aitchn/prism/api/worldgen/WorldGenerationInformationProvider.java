package com.aitchn.prism.api.worldgen;

import com.aitchn.prism.api.behavior.BehaviorOptions;

@FunctionalInterface
public interface WorldGenerationInformationProvider {
    WorldGenerationInformation describe(BehaviorOptions options);

    static WorldGenerationInformationProvider none() {
        return options -> WorldGenerationInformation.empty();
    }
}
