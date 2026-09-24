package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public record ItemInventoryTick(
        PrismKey itemId,
        BehaviorOptions options,
        Player player,
        ItemStack item,
        int slot,
        int intervalTicks,
        com.aitchn.prism.api.registry.RegistryReadView registry,
        ItemEquipmentSnapshot equipment
) implements ItemBehaviorContext {
    public ItemInventoryTick {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(item, "item");
        if (slot < 0 || intervalTicks < 1) {
            throw new IllegalArgumentException("Inventory slot must be non-negative and interval must be positive");
        }
    }

    /** Compatibility constructor; Prism-dispatched contexts always include the captured registry. */
    public ItemInventoryTick(PrismKey itemId, BehaviorOptions options, Player player, ItemStack item, int slot) {
        this(itemId, options, player, item, slot, 1, null);
    }

    /** Compatibility constructor: equipment facts are unavailable outside current Prism dispatch. */
    public ItemInventoryTick(PrismKey itemId, BehaviorOptions options, Player player, ItemStack item,
                             int slot, int intervalTicks, com.aitchn.prism.api.registry.RegistryReadView registry) {
        this(itemId, options, player, item, slot, intervalTicks, registry, null);
    }
}
