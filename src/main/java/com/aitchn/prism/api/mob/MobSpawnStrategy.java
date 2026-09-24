package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.behavior.BehaviorOptions;

public interface MobSpawnStrategy {
    default boolean replaces(MobSpawnEventContext context, BehaviorOptions options) {
        return false;
    }

    default void pulse(MobSpawnPulseContext context, MobSpawnDefinition definition) {
    }
}
