package com.aitchn.prism.api.mob;

import java.util.Objects;
import org.bukkit.entity.Mob;

public record MobContext(Mob entity, MobDefinition definition, MobService mobs) {
    public MobContext {
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(definition, "definition");
        Objects.requireNonNull(mobs, "mobs");
    }
}
