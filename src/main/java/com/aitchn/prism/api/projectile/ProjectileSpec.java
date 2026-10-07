package com.aitchn.prism.api.projectile;

import com.aitchn.prism.api.hit.*;
import java.util.*;
import java.util.function.Predicate;

/** Simulation units are blocks/tick. Gravity applies after movement; drag multiplies the next velocity. */
public record ProjectileSpec(ProjectileAppearance appearance, HitVector velocity, double gravity, double drag,
                             HitVector halfSize, int lifetimeTicks, double maximumDistance, Set<UUID> ignoredTargets,
                             Predicate<HitCandidate> filter, ProjectilePlan plan, ProjectileObserver observer) {
    public ProjectileSpec {
        Objects.requireNonNull(appearance, "appearance"); Objects.requireNonNull(velocity, "velocity");
        Objects.requireNonNull(halfSize, "halfSize"); Objects.requireNonNull(filter, "filter");
        Objects.requireNonNull(plan, "plan"); Objects.requireNonNull(observer, "observer"); ignoredTargets = Set.copyOf(ignoredTargets);
        if (velocity.length() > 64 || !Double.isFinite(gravity) || gravity < 0 || gravity > 4
                || !Double.isFinite(drag) || drag < 0 || drag > 1 || lifetimeTicks < 1 || lifetimeTicks > 1200
                || !Double.isFinite(maximumDistance) || maximumDistance <= 0 || maximumDistance > 16_384
                || ignoredTargets.size() > 255 || halfSize.x() < 0 || halfSize.y() < 0 || halfSize.z() < 0
                || halfSize.x() > 4 || halfSize.y() > 4 || halfSize.z() > 4) throw new IllegalArgumentException("Invalid projectile specification");
    }
}
