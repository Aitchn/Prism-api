package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.behavior.BehaviorOptions;

public interface ItemToolBehavior extends ItemBehaviorHandler {
    ItemToolCapability capability(BehaviorOptions options);

    /** Instance-dependent tools override this; definition-only Guide queries use the original method. */
    default ItemToolCapability capability(BehaviorOptions options, org.bukkit.inventory.ItemStack item) {
        return capability(options);
    }

    default ItemToolCapability capability(BehaviorOptions options, org.bukkit.inventory.ItemStack item,
                                          com.aitchn.prism.api.registry.RegistryReadView registry) {
        return capability(options, item);
    }

    default boolean acceptsRepair(BehaviorOptions options, org.bukkit.inventory.ItemStack product,
                                  org.bukkit.inventory.ItemStack ingredient) {
        return true;
    }

    /** Override when the logical durability differs from the native destruction threshold. */
    default java.util.OptionalInt repairDurability(BehaviorOptions options, org.bukkit.inventory.ItemStack product) {
        return java.util.OptionalInt.empty();
    }

    default java.util.OptionalInt repairDurability(BehaviorOptions options, org.bukkit.inventory.ItemStack product,
                                                   com.aitchn.prism.api.registry.RegistryReadView registry) {
        return repairDurability(options, product);
    }
}
