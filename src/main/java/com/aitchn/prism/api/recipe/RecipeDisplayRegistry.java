package com.aitchn.prism.api.recipe;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Optional;
import org.bukkit.plugin.Plugin;

/** Registration and immutable query surface for Guide recipe displays. */
public interface RecipeDisplayRegistry {
    void register(Plugin owner, PrismKey providerId, RecipeDisplayProvider provider);

    void unregister(Plugin owner);

    /** Requests an index rebuild after one of the owner's providers changes its snapshot. */
    void refresh(Plugin owner);

    long revision();

    Optional<RecipeDisplay> recipe(PrismKey recipeId);

    List<RecipeDisplay> recipesFor(PrismKey result);

    List<RecipeDisplay> usesFor(PrismKey ingredient);
}
