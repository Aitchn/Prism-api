package com.aitchn.prism.api.mob.guide;

import com.aitchn.prism.api.mob.MobDefinition;
import java.util.List;
import java.util.Objects;

public record MobGuideEntry(
        MobDefinition mob,
        MobGuideDefinition guide,
        List<MobGuideSection> sections
) {
    public MobGuideEntry {
        Objects.requireNonNull(mob, "mob");
        Objects.requireNonNull(guide, "guide");
        sections = List.copyOf(Objects.requireNonNull(sections, "sections"));
    }
}
