package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public record ItemOutgoingDamage(
        PrismKey itemId,
        BehaviorOptions options,
        Player player,
        ItemStack item,
        LivingEntity target,
        double damage,
        com.aitchn.prism.api.registry.RegistryReadView registry,
        ItemEquipmentSnapshot equipment
) implements ItemBehaviorContext {
    public ItemOutgoingDamage {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(target, "target");
        if (!Double.isFinite(damage) || damage < 0.0D) {
            throw new IllegalArgumentException("Damage must be finite and non-negative");
        }
    }

    public ItemOutgoingDamage(PrismKey itemId, BehaviorOptions options, Player player, ItemStack item, LivingEntity target, double damage) {
        this(itemId, options, player, item, target, damage, null);
    }

    /** Compatibility constructor: equipment facts are unavailable outside current Prism dispatch. */
    public ItemOutgoingDamage(PrismKey itemId, BehaviorOptions options, Player player, ItemStack item,
                              LivingEntity target, double damage, com.aitchn.prism.api.registry.RegistryReadView registry) {
        this(itemId, options, player, item, target, damage, registry, null);
    }
}
