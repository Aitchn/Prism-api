package com.aitchn.prism.api.fishing;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.component.ComponentOptions;
import com.aitchn.prism.api.data.OptionValues;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Immutable, content declared data for one fish that can be selected by the fishing system.
 * Sizes are exposed as centimetres with one decimal place; the runtime stores tenths exactly.
 */
public record FishingSpeciesDefinition(
        PrismKey itemId,
        PrismKey specimenItemId,
        Set<PrismKey> biomes,
        double minimumSizeCm,
        double maximumSizeCm,
        double sizeDecay,
        double weight,
        Map<PrismKey, Double> baitWeights,
        FishingConditions conditions
) {
    public static final PrismKey TYPE = PrismKey.parse("prism:fishing_species");
    public static final PrismKey SPECIMEN_SIZE = PrismKey.parse("prism:specimen_size");
    public static final PrismKey SPECIMEN_CAUGHT_AT = PrismKey.parse("prism:specimen_caught_at");

    private static final Set<String> OPTION_KEYS = Set.of(
            "biomes", "min-size", "max-size", "decay", "weight", "specimen-item", "bait-weights",
            "active-time", "weather"
    );

    public FishingSpeciesDefinition {
        Objects.requireNonNull(conditions, "conditions");
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(specimenItemId, "specimenItemId");
        biomes = Set.copyOf(Objects.requireNonNull(biomes, "biomes"));
        baitWeights = Map.copyOf(Objects.requireNonNull(baitWeights, "baitWeights"));
        validateSize(minimumSizeCm, "minimumSizeCm");
        validateSize(maximumSizeCm, "maximumSizeCm");
        if (minimumSizeCm > maximumSizeCm) {
            throw new IllegalArgumentException("Fishing minimum size cannot exceed maximum size");
        }
        if (!Double.isFinite(sizeDecay) || sizeDecay < 1.0e-6 || sizeDecay > 100.0) {
            throw new IllegalArgumentException("Fishing size decay must be finite and in [0.000001, 100]");
        }
        if (!Double.isFinite(weight) || weight < 0.0 || weight > 1_000_000.0) {
            throw new IllegalArgumentException("Fishing species weight must be finite and non-negative");
        }
        if (biomes.isEmpty()) {
            throw new IllegalArgumentException("Fishing species must declare at least one biome");
        }
        if (baitWeights.entrySet().stream().anyMatch(entry ->
                !Double.isFinite(entry.getValue()) || entry.getValue() <= 0.0 || entry.getValue() > 1_000_000.0)) {
            throw new IllegalArgumentException("Fishing bait weights must be finite and positive");
        }
    }

    /** Retains the pre-3.20 constructor descriptor for existing addons. */
    public FishingSpeciesDefinition(PrismKey itemId, PrismKey specimenItemId, Set<PrismKey> biomes,
            double minimumSizeCm, double maximumSizeCm, double sizeDecay, double weight,
            Map<PrismKey, Double> baitWeights) {
        this(itemId, specimenItemId, biomes, minimumSizeCm, maximumSizeCm, sizeDecay, weight,
                baitWeights, FishingConditions.UNRESTRICTED);
    }

    /** Validate the component options while the registry candidate is still in PREPARE. */
    public static void validate(ComponentOptions options) {
        parse(null, options);
    }

    /** Parse content options and associate them with the declaring item. */
    public static FishingSpeciesDefinition from(PrismKey itemId, ComponentOptions options) {
        return parse(Objects.requireNonNull(itemId, "itemId"), options);
    }

    private static FishingSpeciesDefinition parse(PrismKey itemId, ComponentOptions options) {
        Objects.requireNonNull(options, "options");
        if (!OPTION_KEYS.containsAll(options.values().keySet())) {
            Set<String> unknown = new LinkedHashSet<>(options.values().keySet());
            unknown.removeAll(OPTION_KEYS);
            throw new IllegalArgumentException("Unknown fishing species option(s): " + unknown);
        }
        Object rawBiomes = options.values().get("biomes");
        if (!(rawBiomes instanceof List<?> list) || list.isEmpty()
                || list.stream().anyMatch(value -> !(value instanceof String))) {
            throw new IllegalArgumentException("Fishing species biomes must be a non-empty string list");
        }
        Set<PrismKey> biomes = new LinkedHashSet<>();
        for (Object rawBiome : list) {
            try {
                biomes.add(PrismKey.parse((String) rawBiome));
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("Fishing species biome must be a namespaced ID: " + rawBiome,
                        exception);
            }
        }
        double minimum = size(options, "min-size", 0.1);
        double maximum = size(options, "max-size", minimum);
        double decay = options.values().containsKey("decay")
                ? OptionValues.requireDouble(options.values().get("decay"), "decay") : 4.0;
        double weight = options.values().containsKey("weight")
                ? OptionValues.requireDouble(options.values().get("weight"), "weight") : 1.0;
        PrismKey specimen = null;
        Object rawSpecimen = options.values().get("specimen-item");
        if (rawSpecimen != null) {
            if (!(rawSpecimen instanceof String value)) {
                throw new IllegalArgumentException("Fishing species specimen-item must be a namespaced ID");
            }
            specimen = PrismKey.parse(value);
        }
        if (specimen == null && itemId != null) {
            specimen = new PrismKey(itemId.namespace(), itemId.value() + "_specimen");
        }
        if (specimen == null) {
            // Validation without a declaring ID still verifies the complete option shape.
            specimen = PrismKey.parse("prism:placeholder_specimen");
        }
        Map<PrismKey, Double> baitWeights = new LinkedHashMap<>();
        Object rawBaitWeights = options.values().get("bait-weights");
        if (rawBaitWeights != null) {
            if (!(rawBaitWeights instanceof Map<?, ?> map) || map.isEmpty()) {
                throw new IllegalArgumentException("Fishing bait-weights must be a non-empty object");
            }
            map.forEach((rawKey, rawValue) -> {
                if (!(rawKey instanceof String key)) {
                    throw new IllegalArgumentException("Fishing bait weight keys must be namespaced IDs");
                }
                baitWeights.put(PrismKey.parse(key), OptionValues.requireDouble(rawValue, "bait-weights." + key));
            });
        }
        if (itemId == null) {
            // Keep validation independent from a content item while still checking all numeric constraints.
            itemId = PrismKey.parse("prism:placeholder_fish");
        }
        return new FishingSpeciesDefinition(itemId, specimen, biomes, minimum, maximum, decay, weight, baitWeights,
                FishingConditions.parse(options.values()));
    }

    private static double size(ComponentOptions options, String key, double fallback) {
        double value = options.values().containsKey(key)
                ? OptionValues.requireDouble(options.values().get(key), key) : fallback;
        validateSize(value, key);
        double tenths = Math.rint(value * 10.0);
        if (Math.abs(value * 10.0 - tenths) > 1.0e-7) {
            throw new IllegalArgumentException("Fishing " + key + " must have at most one decimal place");
        }
        return tenths / 10.0;
    }

    private static void validateSize(double value, String name) {
        if (!Double.isFinite(value) || value < 0.1 || value > 100_000.0) {
            throw new IllegalArgumentException("Fishing " + name + " must be finite and in [0.1, 100000]");
        }
    }
}
