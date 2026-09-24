package com.aitchn.prism.api.mob;

import java.time.Clock;
import java.util.Objects;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

public record MobSpawnEventContext(
        LivingEntity entity,
        CreatureSpawnEvent.SpawnReason cause,
        Clock clock
) {
    public MobSpawnEventContext {
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(cause, "cause");
        Objects.requireNonNull(clock, "clock");
    }
}
