package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.data.OptionValues;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * One compatible product configuration for a trait's Guide text. Built only from immutable registry data.
 *
 * @param tiers the product's configured {@code traits.thresholds} tiers for this trait, in ascending order
 * @param options the product's {@code traits.options} entry for this trait
 * @param productOptions the product's complete behavior options, or {@code null} when no compatible product exists
 */
public record ForgingTraitGuideContext(PrismKey trait, List<Tier> tiers, Map<String, Object> options,
                                      BehaviorOptions productOptions) {
    public record Tier(int required, double value) {
    }

    public ForgingTraitGuideContext {
        Objects.requireNonNull(trait, "trait");
        tiers = List.copyOf(tiers);
        options = OptionValues.immutableMap(Objects.requireNonNull(options, "options"));
    }

    public boolean tiered() {
        return !tiers.isEmpty();
    }

    /** Whether a compatible product supplies this context. */
    public boolean configured() {
        return productOptions != null;
    }
}
