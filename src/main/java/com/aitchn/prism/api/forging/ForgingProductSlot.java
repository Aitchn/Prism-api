package com.aitchn.prism.api.forging;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;

/** Where a product applies its attributes. Armor slots also supply loadout environment and impact statistics. */
public enum ForgingProductSlot {
    MAINHAND(EquipmentSlot.HAND, EquipmentSlotGroup.MAINHAND),
    OFFHAND(EquipmentSlot.OFF_HAND, EquipmentSlotGroup.OFFHAND),
    HEAD(EquipmentSlot.HEAD, EquipmentSlotGroup.HEAD),
    CHEST(EquipmentSlot.CHEST, EquipmentSlotGroup.CHEST),
    LEGS(EquipmentSlot.LEGS, EquipmentSlotGroup.LEGS),
    FEET(EquipmentSlot.FEET, EquipmentSlotGroup.FEET);

    private final EquipmentSlot slot;
    private final EquipmentSlotGroup group;

    ForgingProductSlot(EquipmentSlot slot, EquipmentSlotGroup group) {
        this.slot = slot;
        this.group = group;
    }

    public EquipmentSlot equipmentSlot() {
        return slot;
    }

    public EquipmentSlotGroup group() {
        return group;
    }

    public boolean armor() {
        return this == HEAD || this == CHEST || this == LEGS || this == FEET;
    }
}
