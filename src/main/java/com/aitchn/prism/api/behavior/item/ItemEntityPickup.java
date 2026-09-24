package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.behavior.BehaviorPhase;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import org.bukkit.entity.Item;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;

/** Immutable facts supplied before and during a custom inventory-mob pickup. */
public record ItemEntityPickup(
        PrismKey itemId,
        BehaviorOptions options,
        Mob entity,
        Item source,
        ItemStack item,
        int amount,
        BehaviorPhase phase,
        BooleanSupplier active
) {
    public ItemEntityPickup {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(source, "source");
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(phase, "phase");
        Objects.requireNonNull(active, "active");
        if (amount <= 0) {
            throw new IllegalArgumentException("Pickup amount must be positive");
        }
        item = item.clone();
    }
}
