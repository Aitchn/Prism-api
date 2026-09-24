package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/** Both entities are owned by the dispatching region. Denial cancels only this target selection. */
public record ItemTargeting(PrismKey itemId, BehaviorOptions options, ItemStack item,
                            Player player, LivingEntity source, EquipmentSlot slot,
                            EntityTargetEvent.TargetReason reason,
                            com.aitchn.prism.api.registry.RegistryReadView registry, ItemEquipmentSnapshot equipment) implements ItemBehaviorContext {
    public ItemTargeting {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(slot, "slot");
        Objects.requireNonNull(reason, "reason");
    }

    public ItemTargeting(PrismKey itemId, BehaviorOptions options, ItemStack item, Player player, LivingEntity source,
                         EquipmentSlot slot, EntityTargetEvent.TargetReason reason) {
        this(itemId, options, item, player, source, slot, reason, null);
    }

    /** Compatibility constructor: equipment facts are unavailable outside current Prism dispatch. */
    public ItemTargeting(PrismKey itemId, BehaviorOptions options, ItemStack item, Player player, LivingEntity source,
                         EquipmentSlot slot, EntityTargetEvent.TargetReason reason,
                         com.aitchn.prism.api.registry.RegistryReadView registry) {
        this(itemId, options, item, player, source, slot, reason, registry, null);
    }
}
