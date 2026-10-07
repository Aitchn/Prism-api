package com.aitchn.prism.api.hit;

import java.util.List;
import java.util.Optional;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

/** Query-only volumes for players and living mobs. Every entity operation requires its entity owner. */
public interface HitboxService {
    int MAX_PARTS = 16;
    HitboxHandle attach(Plugin owner, LivingEntity target, List<HitVolume> volumes);
    Optional<HitboxHandle> find(LivingEntity target);
    interface HitboxHandle extends AutoCloseable {
        List<HitVolume> volumes();
        long version();
        boolean attached();
        /** Atomic replacement; validates before replacing. Requires the target's entity owner. */
        void replace(List<HitVolume> volumes);
        /** Requires the target's entity owner. Idempotent. Owner disable also revokes the binding. */
        @Override void close();
    }
}
