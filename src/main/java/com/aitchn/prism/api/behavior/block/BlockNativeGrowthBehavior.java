package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.behavior.BehaviorResult;

/** Handles a native block growth transaction while preserving Prism identity. */
public interface BlockNativeGrowthBehavior extends BlockBehaviorHandler {
    BehaviorResult nativeGrow(BlockNativeGrowthContext context);
}
