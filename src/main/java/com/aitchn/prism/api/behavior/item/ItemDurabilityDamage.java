package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public record ItemDurabilityDamage(
        PrismKey itemId,
        BehaviorOptions options,
        Player player,
        ItemStack item,
        int damage,
        DurabilityCause cause,
        com.aitchn.prism.api.registry.RegistryReadView registry
) implements ItemBehaviorContext {
    public ItemDurabilityDamage {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(cause, "cause");
        if (damage < 0) {
            throw new IllegalArgumentException("Durability damage cannot be negative");
        }
    }

    public ItemDurabilityDamage(PrismKey itemId, BehaviorOptions options, Player player, ItemStack item, int damage, DurabilityCause cause) {
        this(itemId, options, player, item, damage, cause, null);
    }
}
