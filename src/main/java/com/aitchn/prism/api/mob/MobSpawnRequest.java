package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public record MobSpawnRequest(
        PrismKey mob,
        Location location,
        PrismKey cause,
        @Nullable Entity summoner
) {
    public MobSpawnRequest {
        Objects.requireNonNull(mob, "mob");
        location = Objects.requireNonNull(location, "location").clone();
        Objects.requireNonNull(location.getWorld(), "Mob spawn locations must have a world");
        Objects.requireNonNull(cause, "cause");
    }

    @Override
    public Location location() {
        return location.clone();
    }
}
