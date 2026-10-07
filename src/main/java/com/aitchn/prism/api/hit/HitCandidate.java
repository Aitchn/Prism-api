package com.aitchn.prism.api.hit;

import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;

/** Immutable region/entity-owned observation. Entity and part candidates share one target identity. */
public record HitCandidate(Kind kind, UUID world, UUID target, String part, double damageMultiplier,
                           HitBox bounds, HitIntersection intersection, long version, long capturedNanos,
                           int blockX, int blockY, int blockZ) {
    public enum Kind { BLOCK, MOB_PART, PART, BODY }
    public static final Comparator<HitCandidate> ORDER = Comparator
            .comparingDouble((HitCandidate value) -> value.intersection().t())
            .thenComparingInt(value -> value.kind() == Kind.BLOCK ? 0 : value.kind() == Kind.BODY ? 2 : 1)
            .thenComparing(value -> value.target() == null ? "" : value.target().toString())
            .thenComparing(value -> value.part() == null ? "" : value.part())
            .thenComparingInt(HitCandidate::blockX).thenComparingInt(HitCandidate::blockY).thenComparingInt(HitCandidate::blockZ);
    public HitCandidate {
        Objects.requireNonNull(kind, "kind"); Objects.requireNonNull(world, "world");
        Objects.requireNonNull(bounds, "bounds"); Objects.requireNonNull(intersection, "intersection");
        if ((kind == Kind.BLOCK) != (target == null) || (kind == Kind.PART || kind == Kind.MOB_PART) != (part != null)
                || !Double.isFinite(damageMultiplier) || damageMultiplier < .01 || damageMultiplier > 10 || version < 0) {
            throw new IllegalArgumentException("Invalid hit candidate");
        }
    }
}
