package com.aitchn.prism.api.recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.aitchn.prism.api.PrismKey;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class RecipeDisplayTest {
    @Test
    void copiesIngredientAlternativesAndRecipeInputs() {
        List<PrismKey> alternatives = new ArrayList<>(List.of(PrismKey.parse("minecraft:stone")));
        RecipeDisplayIngredient ingredient = new RecipeDisplayIngredient(alternatives);
        List<RecipeDisplayIngredient> inputs = new ArrayList<>(List.of(ingredient));
        RecipeDisplay display = new RecipeDisplay(
                PrismKey.parse("test:stonecutting"),
                RecipeDisplayType.STONECUTTING,
                new RecipeDisplayItem(PrismKey.parse("minecraft:stone_slab"), 2),
                inputs
        );

        alternatives.clear();
        inputs.clear();

        assertEquals(List.of(PrismKey.parse("minecraft:stone")), display.inputs().getFirst().alternatives());
        assertThrows(UnsupportedOperationException.class, () -> display.inputs().clear());
    }

    @Test
    void rejectsMalformedLayouts() {
        RecipeDisplayIngredient stone = new RecipeDisplayIngredient(List.of(
                PrismKey.parse("minecraft:stone")
        ));

        assertThrows(IllegalArgumentException.class, () -> new RecipeDisplay(
                PrismKey.parse("test:shaped"),
                RecipeDisplayType.CRAFTING_SHAPED,
                new RecipeDisplayItem(PrismKey.parse("minecraft:stone")),
                List.of(stone)
        ));
        assertThrows(IllegalArgumentException.class, () -> new RecipeDisplay(
                PrismKey.parse("test:smithing"),
                RecipeDisplayType.SMITHING_TRANSFORM,
                new RecipeDisplayItem(PrismKey.parse("minecraft:iron_sword")),
                List.of(stone, stone)
        ));
    }
}
