package com.aitchn.prism.api.item;

import com.aitchn.prism.api.PrismKey;
import java.util.Optional;
import org.bukkit.inventory.ItemStack;

public interface ItemService {
    Optional<PrismKey> identify(ItemStack stack);

    ItemStack create(PrismKey id, int amount);

    /**
     * Legacy in-place normalization has no inventory, actor or location. It is not an authorized
     * world/inventory commit. Use canonicalizedCopy and authorize the actual inventory write.
     */
    @Deprecated
    boolean canonicalize(ItemStack stack);

    /** Normalize detached data; the caller must authorize the later inventory commit. */
    default ItemStack canonicalizedCopy(ItemStack stack) {
        ItemStack copy = stack.clone();
        canonicalize(copy);
        return copy;
    }

    /** Reads supported instance fields using the current immutable content definition. */
    ItemInstanceState state(ItemStack stack);

    /** Returns a canonical copy with validated state, leaving the input stack untouched on failure. */
    ItemStack withState(ItemStack stack, ItemInstanceState state);

    /** Complete applied enchantments, including entries retained while a registered item is inactive. */
    default java.util.Map<PrismKey, Integer> enchantments(ItemStack stack) {
        return stack.getEnchantments().entrySet().stream().collect(java.util.stream.Collectors.toUnmodifiableMap(
                entry -> PrismKey.parse(entry.getKey().getKey().toString()), java.util.Map.Entry::getValue));
    }
}
