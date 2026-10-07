package com.aitchn.prism.api.protection;

import java.util.Objects;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

/** Explicit target role. An instance is an observed identity, never authority to mutate a replacement. */
public record ProtectionTarget(Role role, @Nullable BlockPosition block,
                               @Nullable Entity entity, @Nullable String instanceId,
                               @Nullable java.util.UUID profileId) {
    public ProtectionTarget(Role role, @Nullable BlockPosition block, @Nullable Entity entity,
                            @Nullable String instanceId) {
        this(role, block, entity, instanceId, null);
    }
    public enum Role { TARGET, SOURCE, DESTINATION, FOOTPRINT }

    public ProtectionTarget {
        Objects.requireNonNull(role, "role");
        if (block == null && entity == null && profileId == null) throw new IllegalArgumentException("A target needs a block, entity or profile");
    }

    public static ProtectionTarget block(Role role, BlockPosition position, @Nullable String instance) {
        return new ProtectionTarget(role, Objects.requireNonNull(position), null, instance);
    }

    public static ProtectionTarget profile(java.util.UUID id, long revision) {
        return new ProtectionTarget(Role.TARGET, null, null, Long.toString(revision), Objects.requireNonNull(id));
    }

    public static ProtectionTarget entity(Role role, Entity entity) {
        return new ProtectionTarget(role, null, Objects.requireNonNull(entity), null);
    }
}
