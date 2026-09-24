package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.item.ItemInstanceState;
import com.aitchn.prism.api.registry.RegistryReadView;
import java.util.Objects;

/** Pure, snapshot-bound facts for resolving a weapon or ammunition capability. */
public record ItemProjectileProfile(PrismKey itemId, BehaviorOptions options, ItemInstanceState state,
                                    int wear, RegistryReadView registry, ItemEquipmentSnapshot equipment) {
    public ItemProjectileProfile {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(registry, "registry");
        if (wear < 0) {
            throw new IllegalArgumentException("Projectile profile wear must be non-negative");
        }
    }

    /** Compatibility constructor: equipment facts are unavailable outside current Prism dispatch. */
    public ItemProjectileProfile(PrismKey itemId, BehaviorOptions options, ItemInstanceState state,
                                 int wear, RegistryReadView registry) {
        this(itemId, options, state, wear, registry, null);
    }
}
