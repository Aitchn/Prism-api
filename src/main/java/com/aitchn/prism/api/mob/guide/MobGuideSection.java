package com.aitchn.prism.api.mob.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;

public record MobGuideSection(
        PrismKey id,
        PrismKey title,
        PrismKey iconModel,
        int order,
        List<MobGuideLine> lines
) {
    public MobGuideSection {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(title, "title");
        Objects.requireNonNull(iconModel, "iconModel");
        lines = List.copyOf(Objects.requireNonNull(lines, "lines"));
    }
}
