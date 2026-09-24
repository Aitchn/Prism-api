package com.aitchn.prism.api.recipe;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;

/**
 * An immutable, client-neutral recipe presentation.
 *
 * <p>Shaped crafting uses exactly nine row-major entries and represents blank cells with
 * {@link RecipeDisplayIngredient#empty()}. Other layouts use ordered, non-empty inputs.</p>
 */
public record RecipeDisplay(
        PrismKey id,
        RecipeDisplayType type,
        RecipeDisplayItem result,
        List<RecipeDisplayIngredient> inputs,
        int processingTicks,
        float experience
) {
    public RecipeDisplay {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(result, "result");
        inputs = List.copyOf(inputs);
        if (inputs.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Recipe display inputs cannot contain null");
        }
        validateInputs(type, inputs);
        if (processingTicks < 0) {
            throw new IllegalArgumentException("Recipe display processing ticks cannot be negative");
        }
        if (!Float.isFinite(experience) || experience < 0.0F) {
            throw new IllegalArgumentException("Recipe display experience must be finite and non-negative");
        }
    }

    public RecipeDisplay(
            PrismKey id,
            RecipeDisplayType type,
            RecipeDisplayItem result,
            List<RecipeDisplayIngredient> inputs
    ) {
        this(id, type, result, inputs, 0, 0.0F);
    }

    private static void validateInputs(RecipeDisplayType type, List<RecipeDisplayIngredient> inputs) {
        if (type == RecipeDisplayType.CRAFTING_SHAPED) {
            if (inputs.size() != 9 || inputs.stream().allMatch(RecipeDisplayIngredient::isEmpty)) {
                throw new IllegalArgumentException("Shaped recipe displays require nine cells and one ingredient");
            }
            return;
        }
        int expected = switch (type) {
            case CRAFTING_SHAPELESS -> -1;
            case SMITHING_TRANSFORM -> 3;
            case BREWING -> 2;
            default -> 1;
        };
        if (inputs.isEmpty() || inputs.stream().anyMatch(RecipeDisplayIngredient::isEmpty)) {
            throw new IllegalArgumentException(type + " recipe displays require non-empty ingredients");
        }
        if (expected == -1) {
            int occupiedSlots = inputs.stream().mapToInt(RecipeDisplayIngredient::amount).sum();
            if (occupiedSlots > 9) {
                throw new IllegalArgumentException("Shapeless recipe displays cannot occupy more than nine slots");
            }
        } else if (inputs.size() != expected) {
            throw new IllegalArgumentException(type + " recipe displays require " + expected + " input(s)");
        }
    }
}
