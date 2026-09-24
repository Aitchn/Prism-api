package com.aitchn.prism.api.behavior.item;

public interface ItemOutgoingDamageBehavior extends ItemBehaviorHandler {
    DamageAdjustment outgoingDamage(ItemOutgoingDamage context);
}
