package com.aitchn.prism.api.behavior.item;

import java.util.List;
import java.util.Objects;
import net.kyori.adventure.text.Component;

/** Presentation never becomes canonical identity. Components are immutable; lore is copied. */
public record ItemPresentationResult(Component name, List<Component> lore, boolean hideAttributes) {
    public ItemPresentationResult(Component name, List<Component> lore) {
        this(name, lore, false);
    }

    public ItemPresentationResult {
        Objects.requireNonNull(name, "name");
        lore = List.copyOf(lore);
        if (lore.size() > 128) {
            throw new IllegalArgumentException("Instance presentation is limited to 128 lore lines");
        }
    }
}
