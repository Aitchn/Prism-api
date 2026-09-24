package com.aitchn.prism.api.mob.guide;

import com.aitchn.prism.api.mob.MobDefinition;
import com.aitchn.prism.api.mob.MobSpawnDefinition;
import java.util.List;
import java.util.Objects;

public record MobGuideContext(MobDefinition mob, List<MobSpawnDefinition> spawns) {
    public MobGuideContext {
        Objects.requireNonNull(mob, "mob");
        spawns = List.copyOf(Objects.requireNonNull(spawns, "spawns"));
    }
}
