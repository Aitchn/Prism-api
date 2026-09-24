package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.item.ItemInstanceState;
import com.aitchn.prism.api.registry.RegistryReadView;
import java.util.Objects;

/** Pure captured facts; handlers must not query mutable player or world state. */
public record ItemEnchantingContext(PrismKey itemId, BehaviorOptions options, ItemInstanceState state,
                                    int wear, RegistryReadView registry) {
    public ItemEnchantingContext {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(registry, "registry");
        if (wear < 0) {
            throw new IllegalArgumentException("Wear must be non-negative");
        }
    }
}
