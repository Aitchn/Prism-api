package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.behavior.BehaviorOptions;

public interface MobController {
    default void attach(MobContext context, BehaviorOptions options) {
    }

    default int tickInterval(BehaviorOptions options) {
        return 0;
    }

    default void tick(MobContext context, BehaviorOptions options) {
    }

    default void detach(MobContext context, BehaviorOptions options) {
    }
}
