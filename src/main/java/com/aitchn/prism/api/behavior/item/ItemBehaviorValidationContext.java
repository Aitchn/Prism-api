package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import java.util.Set;
import org.bukkit.Material;

public record ItemBehaviorValidationContext(
        PrismKey itemId,
        Material material,
        Integer maxStackSize,
        Set<PrismKey> vanillaBehaviors,
        BehaviorOptions options
) {
    public ItemBehaviorValidationContext {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(material, "material");
        vanillaBehaviors = Set.copyOf(vanillaBehaviors);
        Objects.requireNonNull(options, "options");
    }
}
