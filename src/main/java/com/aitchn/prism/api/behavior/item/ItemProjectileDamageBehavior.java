package com.aitchn.prism.api.behavior.item;

public interface ItemProjectileDamageBehavior extends ItemBehaviorHandler {
    DamageAdjustment projectileDamage(ItemProjectileDamage context);
}
