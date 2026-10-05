package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.behavior.BehaviorOptions;

public interface MobBehaviorHandler {
    /** Candidate-time validation of cross-binding constraints; must not access runtime entity state. */
    default void validateDefinition(MobDefinition definition) {
    }
    default void onSpawn(MobContext context, BehaviorOptions options) {
    }

    default void onLoad(MobContext context, BehaviorOptions options) {
    }

    default double onAttack(MobAttackContext context, BehaviorOptions options) {
        return context.damage();
    }

    default void onDeath(MobContext context, BehaviorOptions options) {
    }
}
