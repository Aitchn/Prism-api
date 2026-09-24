package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.registry.RegistryReadView;
import java.util.Objects;
import org.bukkit.inventory.EquipmentSlot;

/** Pure facts; reduction is applied to the native event's existing final vector exactly once. */
public record ItemKnockback(PrismKey itemId, BehaviorOptions options, EquipmentSlot slot,
                            RegistryReadView registry, ItemEquipmentSnapshot equipment) {
    public ItemKnockback {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(slot, "slot");
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(equipment, "equipment");
    }
}
