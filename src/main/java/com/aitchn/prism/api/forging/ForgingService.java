package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.item.ItemEquipmentSnapshot;
import com.aitchn.prism.api.item.ItemInstanceState;
import java.util.Optional;
import org.bukkit.inventory.ItemStack;

/**
 * Assembled equipment queries and extension registries. Queries are pure reads of the active content snapshot;
 * callers remain responsible for reading a stack on its owning thread. Invalid or unavailable products resolve
 * to empty rather than throwing.
 */
public interface ForgingService {
    ForgingProductRegistry products();

    ForgingTraitRegistry traits();

    ForgingStatisticRegistry statistics();

    /** Resolves a canonical Prism stack; empty for non-assembled, stale or unavailable products. */
    Optional<ForgingEquipmentView> equipment(ItemStack stack);

    Optional<ForgingEquipmentView> equipment(PrismKey item, ItemInstanceState state, int wear);

    /** The material matrix of a forging part item, or empty when the item is not a part. */
    Optional<ForgingPartView> part(PrismKey item);

    ForgingLoadoutView loadout(ItemEquipmentSnapshot equipment);
}
