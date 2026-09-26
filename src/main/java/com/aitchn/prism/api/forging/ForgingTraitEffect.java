package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.behavior.BehaviorResult;
import com.aitchn.prism.api.behavior.item.ItemHit;
import com.aitchn.prism.api.behavior.item.ItemInventoryTick;
import com.aitchn.prism.api.behavior.item.ItemTargeting;
import java.util.Map;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Gameplay callbacks for a registered trait; every method has a neutral default. Prism calls them only for
 * unbroken products, only while the trait owner is enabled, and on the owner of the originating item callback
 * (the player's entity scheduler or the target's region). Implementations must not retain the contexts.
 */
public interface ForgingTraitEffect {
    ForgingTraitEffect NONE = new ForgingTraitEffect() {
    };

    /** Validates this trait's {@code traits.options} entry during candidate loading. */
    default void validateOptions(Map<String, Object> options) {
        if (!options.isEmpty()) {
            throw new IllegalArgumentException("This forging trait has no options");
        }
    }

    /** Direct melee or projectile damage multiplier. Prism multiplies trait results and caps the product at 1.5. */
    default double outgoingDamageMultiplier(ForgingTraitContext context, LivingEntity target) {
        return 1;
    }

    /** An accepted direct hit; {@code hit.projectile()} distinguishes ranged hits. */
    default void hit(ForgingTraitContext context, ItemHit hit) {
    }

    /**
     * Fraction of incoming damage removed for the loadout leader. Prism combines fractions multiplicatively
     * with the equipment's environment and impact statistics and caps the total reduction at 0.5.
     */
    default double incomingDamageReduction(ForgingTraitContext context, EntityDamageEvent.DamageCause cause) {
        return 0;
    }

    /** Knockback resistance for the loadout leader. Prism sums trait results and caps the total at 1. */
    default double knockbackResistance(ForgingTraitContext context) {
        return 0;
    }

    /** Additional experience from a qualified block break. Prism sums the results. */
    default int bonusMiningExperience(ForgingTraitContext context, int originalExperience) {
        return 0;
    }

    /** Periodic inventory callback for a product carrying this trait or contributing it to a loadout. */
    default void inventoryTick(ForgingTraitContext context, ItemInventoryTick tick) {
    }

    /** Entity targeting of the holder; {@link BehaviorResult#deny()} cancels it. The first non-pass result wins. */
    default BehaviorResult targeted(ForgingTraitContext context, ItemTargeting targeting) {
        return BehaviorResult.pass();
    }

    /** Next-draw time multiplier for ranged weapons. Prism multiplies trait results. */
    default double nextDrawMultiplier(ForgingTraitContext context) {
        return 1;
    }

    /** Whether the complete dropped product resists native fire damage. */
    default boolean fireResistantItem(ForgingTraitContext context) {
        return false;
    }

    /**
     * Product-independent Guide text, shown once below the trait name. When present it replaces the
     * per-product descriptions. Must be pure; it may run on any thread.
     */
    default java.util.Optional<ForgingTraitGuide> guideSummary() {
        return java.util.Optional.empty();
    }

    /**
     * Guide text for one compatible product configuration; empty shows only the trait name. With no compatible
     * product, Prism calls this once with an unconfigured context, and a present result shows Prism's
     * "no compatible product" notice instead. Must be pure; it may run on any thread.
     */
    default java.util.Optional<ForgingTraitGuide> guide(ForgingTraitGuideContext context) {
        return java.util.Optional.empty();
    }

    /** Formats one tier value for {@code {1}} of a tier line. */
    default String formatTierValue(double value) {
        return java.math.BigDecimal.valueOf(value).setScale(2, java.math.RoundingMode.HALF_UP)
                .stripTrailingZeros().toPlainString();
    }
}
