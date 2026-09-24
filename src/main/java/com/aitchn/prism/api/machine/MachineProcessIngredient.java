package com.aitchn.prism.api.machine;

import com.aitchn.prism.api.PrismKey;
import java.util.Set;
import com.aitchn.prism.api.item.ItemInstanceState;

public record MachineProcessIngredient(Set<PrismKey> items, Set<PrismKey> tags, int amount, ItemInstanceState state) {
    public MachineProcessIngredient {
        java.util.Objects.requireNonNull(state, "state");
        items = Set.copyOf(items);
        tags = Set.copyOf(tags);
        if (items.isEmpty() == tags.isEmpty()) {
            throw new IllegalArgumentException("A machine process ingredient requires exactly one item or tag set");
        }
        if (amount < 1) {
            throw new IllegalArgumentException("A machine process ingredient amount must be positive");
        }
    }

    public MachineProcessIngredient(Set<PrismKey> items, Set<PrismKey> tags, int amount) {
        this(items, tags, amount, ItemInstanceState.empty());
    }
}
