package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Immutable aggregation over the captured armor slots and hands. Broken products contribute nothing.
 *
 * @param leader first slot holding an unbroken product; loadout-wide effects dispatch only from it, or {@code null}
 * @param environmentResistance summed worn-armor environment resistance, clamped to 0..0.25
 * @param impactBuffer summed worn-armor impact buffer, clamped to 0..0.25
 */
public record ForgingLoadoutView(Map<PrismKey, Integer> traits, EquipmentSlot leader,
                                 double environmentResistance, double impactBuffer) {
    public static final ForgingLoadoutView EMPTY = new ForgingLoadoutView(Map.of(), null, 0, 0);

    public ForgingLoadoutView {
        traits = Map.copyOf(traits);
    }

    public int trait(PrismKey trait) {
        return traits.getOrDefault(trait, 0);
    }
}
