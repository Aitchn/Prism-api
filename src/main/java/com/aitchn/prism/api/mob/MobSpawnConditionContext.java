package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import java.time.Clock;
import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public record MobSpawnConditionContext(
        Location location,
        PrismKey cause,
        Clock clock,
        @Nullable Entity source
) {
    public MobSpawnConditionContext {
        location = Objects.requireNonNull(location, "location").clone();
        Objects.requireNonNull(location.getWorld(), "Mob condition locations must have a world");
        Objects.requireNonNull(cause, "cause");
        Objects.requireNonNull(clock, "clock");
    }

    @Override
    public Location location() {
        return location.clone();
    }
}
