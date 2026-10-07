package com.aitchn.prism.api.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.component.ComponentOptions;
import java.util.Objects;
import java.util.Set;

/**
 * Immutable data of the {@code prism:energy_storage} item component.
 *
 * <p>The stored amount is per-stack instance data owned by Prism; read and change it through
 * {@link EnergyService}. {@code resource} names either a substance (for example {@code prism:steam}) or a
 * scalar machine resource type (for example {@code prism:power}). {@code useCost} is optional metadata that
 * behaviors may read as their default per-use cost; it is not charged automatically.</p>
 *
 * <pre>{@code
 * components:
 *   prism:energy_storage:
 *     resource: prism:steam
 *     capacity: 4000
 *     use-cost: 10
 * }</pre>
 *
 * @since 3.25 (engine build 0.9.63)
 */
public record EnergyStorage(PrismKey resource, long capacity, long useCost) {
    public static final PrismKey TYPE = PrismKey.parse("prism:energy_storage");
    public static final long MAXIMUM_CAPACITY = 1_000_000_000L;
    private static final Set<String> KEYS = Set.of("resource", "capacity", "use-cost");

    public EnergyStorage {
        Objects.requireNonNull(resource, "resource");
        if (capacity < 1L || capacity > MAXIMUM_CAPACITY) {
            throw new IllegalArgumentException("prism:energy_storage capacity must be between 1 and " + MAXIMUM_CAPACITY);
        }
        if (useCost < 0L || useCost > capacity) {
            throw new IllegalArgumentException("prism:energy_storage use-cost must be between 0 and the capacity");
        }
    }

    /** Parses and strictly validates component options; unknown keys are rejected. */
    public static EnergyStorage from(ComponentOptions options) {
        Objects.requireNonNull(options, "options");
        for (String key : options.values().keySet()) {
            if (!KEYS.contains(key)) {
                throw new IllegalArgumentException("Unknown prism:energy_storage option: " + key);
            }
        }
        if (!options.values().containsKey("resource") || !options.values().containsKey("capacity")) {
            throw new IllegalArgumentException("prism:energy_storage requires resource and capacity");
        }
        PrismKey resource = PrismKey.parse(options.requireString("resource"));
        long capacity = options.requireInt("capacity");
        long useCost = options.values().containsKey("use-cost") ? options.requireInt("use-cost") : 0L;
        return new EnergyStorage(resource, capacity, useCost);
    }
}
