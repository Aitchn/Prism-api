package com.aitchn.prism.api.behavior.item;

/** Returns a finite multiplier in [0, 1] for incoming knockback from the owner's six equipment slots. */
public interface ItemKnockbackBehavior extends ItemBehaviorHandler {
    double knockbackMultiplier(ItemKnockback context);
}
