package com.aitchn.prism.api.item;

import java.util.Optional;
import org.bukkit.inventory.ItemStack;

/**
 * Reads and changes the energy held by {@code prism:energy_storage} items.
 *
 * <p>Mutating calls change the given stack in place and accept only a single registered energy item
 * (amount one). The caller must own the stack (for example the player's entity scheduler for a held item)
 * and authorize the inventory commit; this service performs no protection checks. Amounts never leave the
 * range zero to capacity, and an item that holds no energy carries no extra data.</p>
 *
 * @since 3.25 (engine build 0.9.63)
 */
public interface EnergyService {
    /** The component of a registered energy item, or empty for any other stack. */
    Optional<EnergyStorage> storage(ItemStack stack);

    /** Stored units, or {@code 0} for a stack without energy storage. */
    long stored(ItemStack stack);

    /** Adds up to {@code amount} units and returns the accepted amount. */
    long insert(ItemStack stack, long amount, boolean simulate);

    /** Removes up to {@code amount} units and returns the removed amount. */
    long extract(ItemStack stack, long amount, boolean simulate);

    /** Removes exactly {@code amount} units, or nothing when fewer are stored. */
    default boolean consume(ItemStack stack, long amount) {
        if (amount < 0L) {
            throw new IllegalArgumentException("Energy amounts cannot be negative");
        }
        if (amount == 0L) {
            return storage(stack).isPresent();
        }
        return extract(stack, amount, true) == amount && extract(stack, amount, false) == amount;
    }
}
