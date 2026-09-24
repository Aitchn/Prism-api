package com.aitchn.prism.api.recipe;

import java.util.Collection;

/**
 * Supplies immutable Guide recipe displays.
 *
 * <p>Prism invokes providers on the Folia global scheduler while rebuilding the display index. Providers must
 * not mutate worlds or entities and must return a complete snapshot for their registration.</p>
 */
@FunctionalInterface
public interface RecipeDisplayProvider {
    Collection<RecipeDisplay> recipes();
}
