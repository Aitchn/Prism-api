package com.aitchn.prism.api.behavior.item;

import java.util.Optional;

/** Supplies native enchantment applicability without changing the canonical item carrier. */
public interface ItemEnchantingBehavior extends ItemBehaviorHandler {
    Optional<ItemEnchantingProfile> enchanting(ItemEnchantingContext context);
}
