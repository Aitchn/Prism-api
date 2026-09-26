package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;
import org.bukkit.attribute.AttributeModifier;

/** An additional attribute modifier in the product's slot. Modifier IDs must be unique within one product. */
public record ForgingAttribute(PrismKey attribute, PrismKey modifier, double amount, AttributeModifier.Operation operation) {
    public ForgingAttribute {
        Objects.requireNonNull(attribute, "attribute");
        Objects.requireNonNull(modifier, "modifier");
        Objects.requireNonNull(operation, "operation");
        if (!Double.isFinite(amount)) {
            throw new IllegalArgumentException("Attribute amounts must be finite");
        }
    }
}
