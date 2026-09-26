package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.behavior.BehaviorResult;
import com.aitchn.prism.api.behavior.item.ItemInteraction;
import com.aitchn.prism.api.behavior.item.ItemMaterialization;
import com.aitchn.prism.api.behavior.item.ItemProjectileProfile;
import com.aitchn.prism.api.behavior.item.RangedAmmunitionProfile;
import com.aitchn.prism.api.behavior.item.RangedWeaponProfile;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Product-specific extensions around Prism's shared assembled-equipment behavior; every method has a neutral
 * default. Prism calls them only while the product owner is enabled, on the owner of the originating item
 * callback. Implementations must not retain the contexts.
 */
public interface ForgingProductHooks {
    ForgingProductHooks NONE = new ForgingProductHooks() {
    };

    /** Validates the product's {@code product-options} during candidate loading. */
    default void validateOptions(Map<String, Object> options) {
        if (!options.isEmpty()) {
            throw new IllegalArgumentException("This forging product has no product-options");
        }
    }

    /** Additional modifiers applied with the standard attributes, including while broken. */
    default List<ForgingAttribute> attributes(ForgingProductContext context) {
        return List.of();
    }

    /**
     * Runs after Prism applies its native components to the detached candidate. Prism clears the consumable
     * and use-effects components before this call; set them here when the product needs a use animation.
     * Throwing rejects the candidate.
     */
    default void materialize(ForgingProductContext context, ItemMaterialization materialization) {
    }

    /** Right-click interaction, including while broken; inspect {@code context.equipment().broken()}. */
    default BehaviorResult interact(ForgingProductContext context, ItemInteraction interaction) {
        return BehaviorResult.pass();
    }

    /** A ranged weapon profile for an unbroken product; {@code nextDrawMultiplier} combines trait results. */
    default Optional<RangedWeaponProfile> rangedWeapon(ForgingProductContext context, ItemProjectileProfile profile,
                                                       double nextDrawMultiplier) {
        return Optional.empty();
    }

    /** An ammunition profile for an unbroken product. */
    default Optional<RangedAmmunitionProfile> ammunition(ForgingProductContext context, ItemProjectileProfile profile) {
        return Optional.empty();
    }
}
