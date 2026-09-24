package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.behavior.BehaviorResult;

public interface BlockPlaceBehavior extends BlockBehaviorHandler {
    BehaviorResult place(BlockPlacement placement);
}
