package com.aitchn.prism.api.recipe;

import com.aitchn.prism.api.PrismKey;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

/** One ingredient with all accepted canonical item alternatives. */
public record RecipeDisplayIngredient(List<PrismKey> alternatives, int amount, com.aitchn.prism.api.item.ItemInstanceState state) {
    public RecipeDisplayIngredient {
        Objects.requireNonNull(alternatives, "alternatives");
        Objects.requireNonNull(state, "state");
        alternatives = List.copyOf(new LinkedHashSet<>(alternatives));
        if (!state.values().isEmpty() && alternatives.size() != 1) {
            throw new IllegalArgumentException("An explicit Guide instance state requires exactly one item alternative");
        }
        if (alternatives.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Recipe display ingredient alternatives cannot contain null");
        }
        if (alternatives.isEmpty()) {
            if (amount != 0 || !state.values().isEmpty()) {
                throw new IllegalArgumentException("An empty recipe display ingredient must have amount 0");
            }
        } else if (amount < 1 || amount > 99) {
            throw new IllegalArgumentException("Recipe display ingredient amount must be from 1 to 99: " + amount);
        }
    }

    public RecipeDisplayIngredient(List<PrismKey> alternatives) {
        this(alternatives, 1);
    }

    public RecipeDisplayIngredient(List<PrismKey> alternatives, int amount) {
        this(alternatives, amount, com.aitchn.prism.api.item.ItemInstanceState.empty());
    }

    public static RecipeDisplayIngredient empty() {
        return new RecipeDisplayIngredient(List.of(), 0);
    }

    public boolean isEmpty() {
        return alternatives.isEmpty();
    }
}
