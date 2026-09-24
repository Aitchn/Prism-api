package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.Material;

public record BlockBehaviorValidationContext(
        PrismKey blockId,
        PrismKey placementItemId,
        Material material,
        boolean movable,
        BehaviorOptions options
) {
    public BlockBehaviorValidationContext {
        Objects.requireNonNull(blockId, "blockId");
        Objects.requireNonNull(placementItemId, "placementItemId");
        Objects.requireNonNull(material, "material");
        Objects.requireNonNull(options, "options");
    }
}
