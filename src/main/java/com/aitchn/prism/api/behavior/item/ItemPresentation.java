package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.item.ItemInstanceState;
import com.aitchn.prism.api.registry.RegistryReadView;
import java.util.Objects;

/** Immutable input and locale-only renderer from the same captured content snapshot. */
public record ItemPresentation(PrismKey itemId, BehaviorOptions options, ItemInstanceState state, int damage,
                               RegistryReadView registry, ItemPresentationResult previous, Text text) {
    public ItemPresentation {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(previous, "previous");
        Objects.requireNonNull(text, "text");
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
    }

    @FunctionalInterface
    public interface Text {
        net.kyori.adventure.text.Component render(PrismKey key, Object... arguments);
    }
}
