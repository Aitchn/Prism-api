package com.aitchn.prism.api.projectile;

import com.aitchn.prism.api.hit.HitVector;
import java.util.*;
import org.bukkit.plugin.Plugin;

/** Packet-only Java ItemDisplay flights and bounded AABB collision; no native Arrow behavior is replaced. */
public interface ProjectileService {
    /** Strictly capability-gated natural projectiles; an empty set means this server has no reviewed native hook. */
    default Set<org.bukkit.entity.EntityType> supportedNativeProjectileTypes() { return Set.of(); }
    /** Bind once on the arrow's owning region. Unsupported types/policies reject rather than change the shield. */
    default NativeBinding bindNative(Plugin owner, org.bukkit.entity.AbstractArrow projectile, NativeProjectileSpec spec) {
        throw new UnsupportedOperationException("This Prism implementation has no native projectile hook");
    }
    interface NativeBinding extends AutoCloseable {
        UUID id();
        Optional<NativeProjectileImpact> lastImpact();
        boolean active();
        /** Immediately revokes callbacks/damage; removes the arrow on its entity scheduler. Never restores ordinary damage. */
        @Override void close();
    }
    ProjectileHandle launch(LaunchContext context, ProjectileSpec spec);
    Optional<ProjectileHandle> find(UUID id);
    int cancel(Plugin owner);
    Metrics metrics();
    record Metrics(int active, long queries, long candidates, long simulationNanos, long waitingNanos,
                   long packets, long viewerSuppressed, Map<ProjectileSnapshot.Removal, Long> removals) {
        public Metrics { removals = Map.copyOf(removals); }
    }
    interface ProjectileHandle {
        UUID id();
        ProjectileSnapshot snapshot();
        /** Queued for the next simulation tick; finite magnitude at most 64 blocks/tick. */
        void velocity(HitVector velocity);
        /** Idempotent; pending query/hit callbacks cannot revive a cancelled flight. */
        void cancel();
    }
}
