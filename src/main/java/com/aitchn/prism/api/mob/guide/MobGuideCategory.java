package com.aitchn.prism.api.mob.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public record MobGuideCategory(
        PrismKey id,
        @Nullable PrismKey parent,
        String displayNameKey,
        List<String> descriptionKeys,
        PrismKey iconModel,
        int order,
        boolean hidden
) {
    public MobGuideCategory {
        Objects.requireNonNull(id, "id");
        if (displayNameKey == null || displayNameKey.isBlank()) {
            throw new IllegalArgumentException("Mob Guide category names must be non-blank");
        }
        descriptionKeys = List.copyOf(Objects.requireNonNull(descriptionKeys, "descriptionKeys"));
        Objects.requireNonNull(iconModel, "iconModel");
    }
}
