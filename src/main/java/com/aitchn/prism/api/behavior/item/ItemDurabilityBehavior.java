package com.aitchn.prism.api.behavior.item;

public interface ItemDurabilityBehavior extends ItemBehaviorHandler {
    DurabilityAdjustment durabilityDamage(ItemDurabilityDamage context);
}
