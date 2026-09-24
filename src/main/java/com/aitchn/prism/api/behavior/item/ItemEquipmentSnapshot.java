package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.item.ItemInstanceState;
import java.util.List;
import java.util.Objects;
import org.bukkit.inventory.EquipmentSlot;

/** Immutable item facts captured on the wearer's owner. No inventories or entities are retained. */
public record ItemEquipmentSnapshot(List<Entry> entries) {
    public static final List<EquipmentSlot> SLOTS = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST,
            EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.HAND, EquipmentSlot.OFF_HAND);
    public static final ItemEquipmentSnapshot EMPTY = new ItemEquipmentSnapshot(List.of());

    public ItemEquipmentSnapshot {
        entries = List.copyOf(entries);
        if (entries.size() > SLOTS.size() || entries.stream().map(Entry::slot).distinct().count() != entries.size()) {
            throw new IllegalArgumentException("Equipment facts require distinct slots among four armor slots and two hands");
        }
    }

    public record Entry(EquipmentSlot slot, PrismKey item, ItemInstanceState state, int wear) {
        public Entry {
            Objects.requireNonNull(slot, "slot");
            Objects.requireNonNull(item, "item");
            Objects.requireNonNull(state, "state");
            if (!SLOTS.contains(slot) || wear < 0) {
                throw new IllegalArgumentException("Invalid equipped item facts");
            }
        }
    }
}
