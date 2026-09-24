package com.aitchn.prism.api.behavior.item;

/** Controls native enchantment activity from immutable item facts; false retains enchantments for later restoration. */
public interface ItemEnchantmentActivityBehavior extends ItemBehaviorHandler {
    boolean enchantmentsActive(ItemEnchantingContext context);
}
