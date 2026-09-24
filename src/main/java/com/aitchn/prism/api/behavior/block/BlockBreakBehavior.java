package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.behavior.BehaviorResult;

public interface BlockBreakBehavior extends BlockBehaviorHandler {
    BehaviorResult breakBlock(BlockBreak blockBreak);
}
