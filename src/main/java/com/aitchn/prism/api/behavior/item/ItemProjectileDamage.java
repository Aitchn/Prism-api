package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.item.ItemInstanceState;
import com.aitchn.prism.api.registry.RegistryReadView;
import java.util.Objects;
import org.bukkit.entity.LivingEntity;

/** Target-owned damage calculation from the arrow's captured ammunition/weapon item facts. */
public record ItemProjectileDamage(PrismKey itemId, BehaviorOptions options, ItemInstanceState state, int wear,
                                   RegistryReadView registry, LivingEntity target, double damage, ItemEquipmentSnapshot equipment, PrismKey weapon) {
    public ItemProjectileDamage {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(target, "target");
        if (wear < 0 || !Double.isFinite(damage) || damage < 0) {
            throw new IllegalArgumentException("Projectile damage facts must be non-negative and finite");
        }
    }

    /** Compatibility constructor: equipment facts are unavailable outside current Prism dispatch. */
    public ItemProjectileDamage(PrismKey itemId, BehaviorOptions options, ItemInstanceState state, int wear,
                                RegistryReadView registry, LivingEntity target, double damage) {
        this(itemId, options, state, wear, registry, target, damage, null);
    }

    public ItemProjectileDamage(PrismKey itemId, BehaviorOptions options, ItemInstanceState state, int wear, RegistryReadView registry, LivingEntity target, double damage, ItemEquipmentSnapshot equipment) {
        this(itemId, options, state, wear, registry, target, damage, equipment, null);
    }
}
