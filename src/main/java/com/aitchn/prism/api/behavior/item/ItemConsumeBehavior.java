package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.behavior.BehaviorResult;

public interface ItemConsumeBehavior extends ItemBehaviorHandler {
    BehaviorResult consume(ItemConsumption interaction);
}
