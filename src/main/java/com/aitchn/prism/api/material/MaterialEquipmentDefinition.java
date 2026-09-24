package com.aitchn.prism.api.material;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorDefinition;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public record MaterialEquipmentDefinition(
        EquipmentType type,
        Set<PrismKey> items,
        List<String> loreKeys,
        List<BehaviorDefinition> traits
) {
    public MaterialEquipmentDefinition(
            EquipmentType type,
            Set<PrismKey> items,
            List<BehaviorDefinition> traits
    ) {
        this(type, items, List.of(), traits);
    }

    public MaterialEquipmentDefinition {
        Objects.requireNonNull(type, "type");
        items = Set.copyOf(Objects.requireNonNull(items, "items"));
        loreKeys = List.copyOf(Objects.requireNonNull(loreKeys, "loreKeys"));
        traits = List.copyOf(Objects.requireNonNull(traits, "traits"));
        if (items.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Material equipment items cannot contain null IDs");
        }
        if (traits.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Material traits cannot contain null definitions");
        }
        if (loreKeys.stream().anyMatch(key -> key == null || key.isBlank())) {
            throw new IllegalArgumentException("Material lore keys must be non-blank");
        }
        long distinctBindings = traits.stream().map(BehaviorDefinition::id).distinct().count();
        if (distinctBindings != traits.size()) {
            throw new IllegalArgumentException("Material trait binding IDs must be unique per equipment type");
        }
    }
}
