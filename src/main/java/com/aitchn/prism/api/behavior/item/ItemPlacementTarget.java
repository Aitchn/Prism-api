package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorDefinition;
import com.aitchn.prism.api.component.ComponentDefinition;
import java.util.List;
import java.util.Objects;
import org.bukkit.Material;

/** Immutable candidate block information available to item registry validators. */
public record ItemPlacementTarget(PrismKey blockId, Material material,
                                  List<ComponentDefinition> components, List<BehaviorDefinition> behaviors) {
    public ItemPlacementTarget {
        Objects.requireNonNull(blockId, "blockId");
        Objects.requireNonNull(material, "material");
        components = List.copyOf(components);
        behaviors = List.copyOf(behaviors);
    }
}
