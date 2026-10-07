package com.aitchn.prism.api.hit;

import java.util.Objects;

/** Owner-position-relative AABB; presentation and click proxies are independent of these query volumes. */
public record HitVolume(String id, HitBox box, double damageMultiplier) {
    public HitVolume {
        Objects.requireNonNull(id, "id"); Objects.requireNonNull(box, "box");
        if (!id.matches("[a-z0-9._-]+") || id.length() > 64) throw new IllegalArgumentException("Invalid part id");
        if (!Double.isFinite(damageMultiplier) || damageMultiplier < .01 || damageMultiplier > 10) {
            throw new IllegalArgumentException("Part multiplier must be .01..10");
        }
        for (HitVector p : java.util.List.of(box.minimum(), box.maximum())) {
            if (Math.abs(p.x()) > 32 || Math.abs(p.y()) > 32 || Math.abs(p.z()) > 32) {
                throw new IllegalArgumentException("Relative part coordinates must be within 32 blocks");
            }
        }
    }
}
