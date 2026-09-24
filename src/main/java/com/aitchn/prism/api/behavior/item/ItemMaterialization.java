package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.item.ItemInstanceState;
import java.util.Objects;
import org.bukkit.inventory.ItemStack;

/** The mutable item is a detached candidate; throwing rejects it before the caller's stack changes. */
public record ItemMaterialization(
        PrismKey itemId,
        BehaviorOptions options,
        ItemStack item,
        ItemInstanceState state,
        int previousDamage,
        boolean initial,
        com.aitchn.prism.api.registry.RegistryReadView registry
) {
    public ItemMaterialization {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(registry, "registry");
        if (previousDamage < 0) {
            throw new IllegalArgumentException("Previous damage cannot be negative");
        }
    }
}
