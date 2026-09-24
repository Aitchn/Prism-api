package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.registry.RegistryReadView;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/** Immutable facts supplied to an item inventory-tick behavior on an inventory mob. */
public record ItemMobInventoryTick(
        PrismKey itemId,
        BehaviorOptions options,
        Mob entity,
        Inventory inventory,
        int slot,
        ItemStack item,
        RegistryReadView registry,
        BooleanSupplier active
) {
    public ItemMobInventoryTick {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(inventory, "inventory");
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(active, "active");
        if (slot < 0) {
            throw new IllegalArgumentException("Inventory slot must be non-negative");
        }
        item = item.clone();
    }
}
