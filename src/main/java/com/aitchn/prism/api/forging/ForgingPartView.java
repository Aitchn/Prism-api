package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Immutable material matrix of one forging part item. */
public record ForgingPartView(PrismKey item, Set<PrismKey> factors, Map<PrismKey, Material> materials) {
    public ForgingPartView {
        Objects.requireNonNull(item, "item");
        factors = Set.copyOf(factors);
        materials = Map.copyOf(materials);
    }

    /** @param trait granted trait, or {@code null} */
    public record Material(Map<PrismKey, Double> statistics, PrismKey trait, int traitValue) {
        public Material {
            statistics = Map.copyOf(statistics);
        }
    }
}
