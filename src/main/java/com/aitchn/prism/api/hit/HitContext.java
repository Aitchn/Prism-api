package com.aitchn.prism.api.hit;

import java.util.*;

/** Immutable attribution and contact facts. A zero incoming vector means there is no defined attack direction. */
public record HitContext(UUID castId, UUID hitId, UUID projectileId, UUID world, UUID source, UUID target,
                         String part, HitVector incoming, HitVector position, HitVector normal,
                         double t, long snapshotVersion, Map<String, String> tags) {
    public HitContext {
        Objects.requireNonNull(castId, "castId"); Objects.requireNonNull(hitId, "hitId");
        Objects.requireNonNull(world, "world"); Objects.requireNonNull(target, "target");
        Objects.requireNonNull(incoming, "incoming"); Objects.requireNonNull(position, "position"); Objects.requireNonNull(normal, "normal");
        tags = Map.copyOf(tags);
        if (tags.size() > 32 || tags.entrySet().stream().anyMatch(entry -> entry.getKey().length() > 64 || entry.getValue().length() > 256)
                || !Double.isFinite(t) || t < 0 || t > 1 || snapshotVersion < 0
                || part != null && (!part.matches("[a-z0-9._-]+") || part.length() > 64)) throw new IllegalArgumentException("Invalid hit context");
    }
}
