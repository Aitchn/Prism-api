package com.aitchn.prism.api.item.guide;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorDefinition;
import com.aitchn.prism.api.component.ComponentDefinition;
import com.aitchn.prism.api.material.EquipmentType;
import com.aitchn.prism.api.material.MaterialDefinition;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public record ItemGuideContext(
        PrismKey item,
        ItemGuideFacts facts,
        List<ComponentDefinition> components,
        List<BehaviorDefinition> behaviors,
        @Nullable MaterialDefinition material,
        @Nullable EquipmentType equipmentType,
        @Nullable com.aitchn.prism.api.registry.RegistryReadView registry
) {
    public ItemGuideContext {
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(facts, "facts");
        components = List.copyOf(Objects.requireNonNull(components, "components"));
        behaviors = List.copyOf(Objects.requireNonNull(behaviors, "behaviors"));
    }

    public ItemGuideContext(PrismKey item, ItemGuideFacts facts, List<ComponentDefinition> components,
                            List<BehaviorDefinition> behaviors, MaterialDefinition material, EquipmentType equipmentType) {
        this(item, facts, components, behaviors, material, equipmentType, null);
    }
}
