package com.aitchn.prism.api.mob;

import java.time.Clock;
import java.util.Objects;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import java.util.function.Predicate;

public record MobSpawnPulseContext(
        Player anchor,
        Clock clock,
        MobService mobs,
        Predicate<Location> conditions
) {
    public MobSpawnPulseContext {
        Objects.requireNonNull(anchor, "anchor");
        Objects.requireNonNull(clock, "clock");
        Objects.requireNonNull(mobs, "mobs");
        Objects.requireNonNull(conditions, "conditions");
    }

    public MobSpawnPulseContext(Player anchor, Clock clock, MobService mobs) {
        this(anchor, clock, mobs, location -> true);
    }

    public boolean conditionsPass(Location location) {
        return conditions.test(Objects.requireNonNull(location, "location").clone());
    }
}
