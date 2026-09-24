package com.aitchn.prism.api.behavior.item;

public interface ItemIncomingDamageBehavior extends ItemBehaviorHandler {
    DamageAdjustment incomingDamage(ItemIncomingDamage context);
}
