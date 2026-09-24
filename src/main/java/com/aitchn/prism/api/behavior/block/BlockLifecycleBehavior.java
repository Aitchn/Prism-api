package com.aitchn.prism.api.behavior.block;

public interface BlockLifecycleBehavior extends BlockBehaviorHandler {
    default void load(BlockLifecycle lifecycle) {
    }

    default void unload(BlockLifecycle lifecycle) {
    }
}
