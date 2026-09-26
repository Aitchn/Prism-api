package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable resolved facts for one assembled product instance.
 *
 * @param materials selected material per part slot
 * @param statistics summed raw contributions
 * @param effectiveStatistics values used by gameplay: zero while broken, otherwise clamped at zero unless signed
 * @param traits summed trait contributions; empty while broken
 */
public record ForgingEquipmentView(PrismKey item, PrismKey product, List<PrismKey> slots, PrismKey core,
                                   Map<PrismKey, PrismKey> materials, int maximumDurability, int wear,
                                   boolean broken, int miningLevel, Map<PrismKey, Double> statistics,
                                   Map<PrismKey, Double> effectiveStatistics, Map<PrismKey, Integer> traits) {
    public ForgingEquipmentView {
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(product, "product");
        Objects.requireNonNull(core, "core");
        slots = List.copyOf(slots);
        materials = Map.copyOf(materials);
        statistics = Map.copyOf(statistics);
        effectiveStatistics = Map.copyOf(effectiveStatistics);
        traits = Map.copyOf(traits);
    }

    public double effectiveStatistic(PrismKey statistic) {
        return effectiveStatistics.getOrDefault(statistic, 0.0D);
    }

    public int trait(PrismKey trait) {
        return traits.getOrDefault(trait, 0);
    }
}
