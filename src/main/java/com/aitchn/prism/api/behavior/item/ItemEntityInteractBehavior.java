package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.behavior.BehaviorResult;

public interface ItemEntityInteractBehavior extends ItemBehaviorHandler {
    BehaviorResult interactEntity(ItemEntityInteraction interaction);
}
