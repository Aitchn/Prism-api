package com.aitchn.prism.api.mob.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;

public record MobGuideDefinition(
        PrismKey mob,
        List<String> descriptionKeys,
        PrismKey iconModel,
        PrismKey category,
        int order,
        boolean hidden
) {
    public MobGuideDefinition {
        Objects.requireNonNull(mob, "mob");
        descriptionKeys = List.copyOf(Objects.requireNonNull(descriptionKeys, "descriptionKeys"));
        descriptionKeys.forEach(key -> {
            if (key == null || key.isBlank()) {
                throw new IllegalArgumentException("Mob Guide description keys must be non-blank");
            }
        });
        Objects.requireNonNull(iconModel, "iconModel");
        Objects.requireNonNull(category, "category");
    }
}
