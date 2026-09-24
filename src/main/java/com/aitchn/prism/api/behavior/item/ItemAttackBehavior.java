package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.behavior.BehaviorResult;

public interface ItemAttackBehavior extends ItemBehaviorHandler {
    BehaviorResult attack(ItemAttack interaction);
}
