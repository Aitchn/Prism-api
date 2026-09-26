package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/**
 * A trait that part materials may grant. Lore renders {@code <namespace>:trait.<value>.name}.
 *
 * @param thresholds accepted tier values, or {@code null} when products may not configure tiers
 */
public record ForgingTraitType(PrismKey id, ForgingTraitThresholds thresholds) {
    public ForgingTraitType {
        Objects.requireNonNull(id, "id");
    }

    public boolean tiered() {
        return thresholds != null;
    }
}
