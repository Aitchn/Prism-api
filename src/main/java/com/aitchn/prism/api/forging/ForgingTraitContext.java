package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.data.OptionValues;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable facts for one trait on one dispatching product.
 *
 * @param amount summed contribution from the dispatching product, or from the captured loadout when present
 * @param tiered whether the dispatching product configures tiers for this trait
 * @param tierValue the highest reached tier value, or zero when tiered and below the first tier
 * @param options this trait's entry under the product's {@code traits.options}
 * @param product the dispatching product's type
 * @param equipment the dispatching product
 * @param loadout captured six-slot facts, or {@code null} when the callback has no equipment snapshot
 * @param productOptions the dispatching product's complete behavior options
 */
public record ForgingTraitContext(PrismKey trait, int amount, boolean tiered, double tierValue,
                                  Map<String, Object> options, ForgingProductType product,
                                  ForgingEquipmentView equipment, ForgingLoadoutView loadout,
                                  BehaviorOptions productOptions) {
    public ForgingTraitContext {
        Objects.requireNonNull(trait, "trait");
        Objects.requireNonNull(product, "product");
        Objects.requireNonNull(equipment, "equipment");
        Objects.requireNonNull(productOptions, "productOptions");
        options = OptionValues.immutableMap(Objects.requireNonNull(options, "options"));
        if (amount < 1) {
            throw new IllegalArgumentException("A dispatched trait requires a positive contribution");
        }
    }

    /** The reached tier value, or {@code untiered} when this product configures no tiers for the trait. */
    public double value(double untiered) {
        return tiered ? tierValue : untiered;
    }
}
