package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.data.OptionValues;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable facts for one product callback.
 *
 * @param fireResistant whether an enabled trait makes this unbroken product resist native fire damage
 * @param options the product's {@code product-options} entry, validated by its hooks
 * @param productOptions the complete {@code prism:assembled_equipment} behavior options
 */
public record ForgingProductContext(ForgingProductType type, ForgingEquipmentView equipment, boolean fireResistant,
                                    Map<String, Object> options, BehaviorOptions productOptions) {
    public ForgingProductContext {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(equipment, "equipment");
        Objects.requireNonNull(productOptions, "productOptions");
        options = OptionValues.immutableMap(Objects.requireNonNull(options, "options"));
    }
}
