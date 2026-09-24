package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.item.ItemInstanceState;
import com.aitchn.prism.api.registry.RegistryReadView;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import org.bukkit.entity.LivingEntity;

/** Accepted direct hit. Only target is region-owned; source is an identity, not a retained entity. */
public record ItemHit(PrismKey itemId, BehaviorOptions options, ItemInstanceState state, int wear,
                      RegistryReadView registry, LivingEntity target, UUID source, boolean projectile,
                      double damage, BooleanSupplier active, ItemEquipmentSnapshot equipment, PrismKey weapon) {
    public ItemHit {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(target, "target");
        Objects.requireNonNull(active, "active");
        if (wear < 0 || !Double.isFinite(damage) || damage <= 0) {
            throw new IllegalArgumentException("A committed hit requires positive final damage and non-negative wear");
        }
    }

    /** Compatibility constructor: equipment facts are unavailable outside current Prism dispatch. */
    public ItemHit(PrismKey itemId, BehaviorOptions options, ItemInstanceState state, int wear,
                   RegistryReadView registry, LivingEntity target, UUID source, boolean projectile,
                   double damage, BooleanSupplier active) {
        this(itemId, options, state, wear, registry, target, source, projectile, damage, active, null);
    }

    public ItemHit(PrismKey itemId, BehaviorOptions options, ItemInstanceState state, int wear, RegistryReadView registry, LivingEntity target, UUID source, boolean projectile, double damage, BooleanSupplier active, ItemEquipmentSnapshot equipment) {
        this(itemId, options, state, wear, registry, target, source, projectile, damage, active, equipment, null);
    }
}
