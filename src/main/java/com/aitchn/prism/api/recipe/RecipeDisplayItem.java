package com.aitchn.prism.api.recipe;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/** An immutable item reference used by a Guide recipe display. */
public record RecipeDisplayItem(PrismKey id, int amount, com.aitchn.prism.api.item.ItemInstanceState state) {
    public RecipeDisplayItem {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(state, "state");
        if (amount < 1 || amount > 99) {
            throw new IllegalArgumentException("Recipe display item amount must be from 1 to 99: " + amount);
        }
    }

    public RecipeDisplayItem(PrismKey id) {
        this(id, 1);
    }

    public RecipeDisplayItem(PrismKey id, int amount) {
        this(id, amount, com.aitchn.prism.api.item.ItemInstanceState.empty());
    }
}
