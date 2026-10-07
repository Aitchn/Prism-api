package com.aitchn.prism.api.projectile;

import com.aitchn.prism.api.hit.*;
import java.util.UUID;

/** Immutable flight state. Removal reasons and the last hit result make unsupported capabilities observable. */
public record ProjectileSnapshot(UUID id, UUID castId, UUID world, UUID source, HitVector position, HitVector velocity,
                                 State state, int simulatedTicks, double travelled, Removal removal, HitResult lastHit, HitQueryResult.Status queryStatus) {
    public ProjectileSnapshot {
        java.util.Objects.requireNonNull(id, "id"); java.util.Objects.requireNonNull(castId, "castId");
        java.util.Objects.requireNonNull(world, "world"); java.util.Objects.requireNonNull(position, "position");
        java.util.Objects.requireNonNull(velocity, "velocity"); java.util.Objects.requireNonNull(state, "state");
        java.util.Objects.requireNonNull(removal, "removal");
        if (simulatedTicks < 0 || simulatedTicks > 1200 || !Double.isFinite(travelled) || travelled < 0 || travelled > 16384.000001
                || (state == State.REMOVED) != (removal != Removal.NONE)) throw new IllegalArgumentException("Invalid projectile snapshot");
    }
    public ProjectileSnapshot(UUID id, UUID castId, UUID world, UUID source, HitVector position, HitVector velocity,
                              State state, int simulatedTicks, double travelled, Removal removal, HitResult lastHit) {
        this(id, castId, world, source, position, velocity, state, simulatedTicks, travelled, removal, lastHit, null);
    }
    public enum State { FLYING, WAITING, REMOVED }
    public enum Removal { NONE, CANCELLED, HIT, BLOCK, LIFETIME, DISTANCE, OWNER_DISABLED, QUERY_FAILED,
        HIT_UNSUPPORTED, BUDGET_EXCEEDED, PLAN_FAILED, OBSERVER_FAILED, CLOSED }
}
