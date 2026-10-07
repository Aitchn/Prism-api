package com.aitchn.prism.api.projectile;

import com.aitchn.prism.api.hit.HitVector;
import java.util.*;
import org.bukkit.plugin.Plugin;

/** Launch requires the starting region. Caster death/logout/teleport does not bind this flight's lifetime. */
public record LaunchContext(Plugin owner, UUID world, HitVector start, UUID source, Map<String, String> tags) {
    public LaunchContext {
        Objects.requireNonNull(owner, "owner"); Objects.requireNonNull(world, "world"); Objects.requireNonNull(start, "start"); tags = Map.copyOf(tags);
        if (tags.size() > 32 || tags.entrySet().stream().anyMatch(entry -> entry.getKey().length() > 64 || entry.getValue().length() > 256)
                || Math.abs(start.x()) > 29_999_900 || Math.abs(start.y()) > 29_999_900 || Math.abs(start.z()) > 29_999_900) {
            throw new IllegalArgumentException("Invalid launch context");
        }
    }
}
