package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.behavior.BehaviorResult;

public interface ItemBlockBreakBehavior extends ItemBehaviorHandler {
    BehaviorResult breakBlock(ItemBlockBreak interaction);
}
