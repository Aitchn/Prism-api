package com.aitchn.prism.api.hit;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

/** Complete segment query. Filter executes against immutable candidates and must not access Bukkit state. */
public record HitQuery(UUID world, HitVector from, HitVector to, HitVector halfSize,
                       Set<UUID> ignoredTargets, Predicate<HitCandidate> filter) {
    public HitQuery {
        Objects.requireNonNull(world, "world"); Objects.requireNonNull(from, "from"); Objects.requireNonNull(to, "to");
        Objects.requireNonNull(halfSize, "halfSize"); Objects.requireNonNull(filter, "filter");
        ignoredTargets = Set.copyOf(ignoredTargets);
        if (ignoredTargets.size() > 256 || to.subtract(from).length() > 64) throw new IllegalArgumentException("Query budget exceeded");
        if (halfSize.x() < 0 || halfSize.y() < 0 || halfSize.z() < 0
                || halfSize.x() > 4 || halfSize.y() > 4 || halfSize.z() > 4) throw new IllegalArgumentException("Half size must be 0..4");
        for (HitVector p : java.util.List.of(from, to)) {
            if (Math.abs(p.x()) > 29_999_900 || Math.abs(p.y()) > 29_999_900 || Math.abs(p.z()) > 29_999_900) {
                throw new IllegalArgumentException("Query outside supported world coordinates");
            }
        }
    }
}
