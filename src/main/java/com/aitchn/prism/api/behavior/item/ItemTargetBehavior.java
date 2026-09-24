package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.behavior.BehaviorResult;

public interface ItemTargetBehavior extends ItemBehaviorHandler {
    BehaviorResult targeted(ItemTargeting context);
}
