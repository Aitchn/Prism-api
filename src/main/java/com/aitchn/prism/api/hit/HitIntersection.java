package com.aitchn.prism.api.hit;

import java.util.Objects;

/** Segment fraction, first contact and outward face normal. Embedded starts have the zero normal. */
public record HitIntersection(double t, HitVector position, HitVector normal) {
    public HitIntersection {
        Objects.requireNonNull(position, "position");
        Objects.requireNonNull(normal, "normal");
        if (!Double.isFinite(t) || t < 0 || t > 1) throw new IllegalArgumentException("Invalid segment fraction");
    }
}
