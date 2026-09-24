package com.aitchn.prism.api.worldgen;

@FunctionalInterface
public interface WorldGenerationValidator {
    void validate(WorldGenerationValidationContext context);

    static WorldGenerationValidator acceptingAnyOptions() {
        return context -> {
        };
    }
}
