package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public record ItemIncomingDamage(
        PrismKey itemId,
        BehaviorOptions options,
        Player player,
        ItemStack item,
        EquipmentSlot slot,
        LivingEntity attacker,
        EntityDamageEvent.DamageCause cause,
        double damage,
        com.aitchn.prism.api.registry.RegistryReadView registry,
        ItemEquipmentSnapshot equipment
) implements ItemBehaviorContext {
    public ItemIncomingDamage {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(slot, "slot");
        Objects.requireNonNull(cause, "cause");
        if (!Double.isFinite(damage) || damage < 0.0D) {
            throw new IllegalArgumentException("Damage must be finite and non-negative");
        }
    }

    /** Compatibility constructor: equipment facts are unavailable outside current Prism dispatch. */
    public ItemIncomingDamage(PrismKey itemId, BehaviorOptions options, Player player, ItemStack item,
                              EquipmentSlot slot, LivingEntity attacker, EntityDamageEvent.DamageCause cause, double damage) {
        this(itemId, options, player, item, slot, attacker, cause, damage, null, null);
    }
}
