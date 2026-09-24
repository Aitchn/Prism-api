package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.item.ItemInstanceState;
import com.aitchn.prism.api.registry.RegistryReadView;
import java.util.Objects;

/** Pure facts after the original drop calculation has applied mining and enchantment gates. */
public record ItemMiningExperience(
        PrismKey itemId, BehaviorOptions options, ItemInstanceState state, int damage,
        RegistryReadView registry, int originalExperience, int experience, ItemEquipmentSnapshot equipment
) {
    public ItemMiningExperience {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(registry, "registry");
        if (damage < 0 || originalExperience <= 0 || experience < 0) {
            throw new IllegalArgumentException("Mining experience requires a positive original drop and non-negative facts");
        }
    }

    /** Compatibility constructor: equipment facts are unavailable outside current Prism dispatch. */
    public ItemMiningExperience(PrismKey itemId, BehaviorOptions options, ItemInstanceState state, int damage,
                                RegistryReadView registry, int originalExperience, int experience) {
        this(itemId, options, state, damage, registry, originalExperience, experience, null);
    }
}
