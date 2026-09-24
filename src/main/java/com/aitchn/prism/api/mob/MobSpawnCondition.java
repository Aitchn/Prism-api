package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.behavior.BehaviorOptions;

@FunctionalInterface
public interface MobSpawnCondition {
    boolean test(MobSpawnConditionContext context, BehaviorOptions options);
}
