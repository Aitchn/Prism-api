package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import java.util.Set;
import java.util.Map;
import java.util.List;
import com.aitchn.prism.api.component.ComponentDefinition;

/** Context available after every item definition in a candidate registry has been loaded. */
public record ItemBehaviorRegistryValidationContext(
        PrismKey itemId,
        Set<PrismKey> registeredItems,
        BehaviorOptions options,
        Map<PrismKey, List<ComponentDefinition>> itemComponents,
        List<com.aitchn.prism.api.behavior.BehaviorDefinition> behaviors,
        Map<PrismKey, List<ItemPlacementTarget>> placementTargets
) {
    public ItemBehaviorRegistryValidationContext {
        Objects.requireNonNull(itemId, "itemId");
        registeredItems = Set.copyOf(registeredItems);
        Objects.requireNonNull(options, "options");
        itemComponents = itemComponents.entrySet().stream().collect(java.util.stream.Collectors.toUnmodifiableMap(
                Map.Entry::getKey, entry -> List.copyOf(entry.getValue())));
        behaviors = List.copyOf(behaviors);
        placementTargets = placementTargets.entrySet().stream().collect(java.util.stream.Collectors.toUnmodifiableMap(
                Map.Entry::getKey, entry -> List.copyOf(entry.getValue())));
    }

    public ItemBehaviorRegistryValidationContext(PrismKey itemId, Set<PrismKey> registeredItems, BehaviorOptions options,
                                                Map<PrismKey, List<ComponentDefinition>> itemComponents,
                                                List<com.aitchn.prism.api.behavior.BehaviorDefinition> behaviors) {
        this(itemId, registeredItems, options, itemComponents, behaviors, Map.of());
    }

    public ItemBehaviorRegistryValidationContext(PrismKey itemId, Set<PrismKey> registeredItems, BehaviorOptions options) {
        this(itemId, registeredItems, options, Map.of(), List.of());
    }

    public ItemBehaviorRegistryValidationContext(PrismKey itemId, Set<PrismKey> registeredItems, BehaviorOptions options,
                                                Map<PrismKey, List<ComponentDefinition>> itemComponents) {
        this(itemId, registeredItems, options, itemComponents, List.of());
    }
}
