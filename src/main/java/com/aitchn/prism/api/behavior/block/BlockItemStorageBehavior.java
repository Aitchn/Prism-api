package com.aitchn.prism.api.behavior.block;

import java.util.List;
import org.bukkit.inventory.ItemStack;

/**
 * Exposes a custom block's item storage to Prism logistics (API 3.24). Industrial node item endpoints
 * reach a block through this capability instead of treating its native carrier as a container.
 *
 * <p>Prism calls the handler on the block's owning region, only while the binding's owner is enabled and
 * the block still carries the bound content identity. Stacks are canonical and compared exactly
 * ({@link ItemStack#isSimilar}); base-material matching is never implied. A simulated call must not mutate
 * state, and a committed call must not move more than its simulation reported unless the storage changed
 * in between. Returned amounts outside {@code 0..amount} are clamped and a handler failure counts as zero.</p>
 */
public interface BlockItemStorageBehavior extends BlockBehaviorHandler {
    /**
     * Snapshot of stacks that may leave through {@code storage.face()}. Each amount is a quantity available
     * now; an implementation may report less than it holds (for example one stack per entry). Prism retains
     * only copies and caps each transfer at the stack's maximum size.
     */
    List<ItemStack> extractable(BlockItemStorage storage);

    /** Inserts up to {@code amount} items exactly similar to {@code stack} and returns the accepted amount. */
    int insert(BlockItemStorage storage, ItemStack stack, int amount, boolean simulate);

    /** Extracts up to {@code amount} items exactly similar to {@code stack} and returns the removed amount. */
    int extract(BlockItemStorage storage, ItemStack stack, int amount, boolean simulate);
}
