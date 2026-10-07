package com.aitchn.prism.api.protection;

import java.util.Objects;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

/** Actual actor references, used only on an execution owner. Neither owner UUID nor cause is a grant. */
public record ProtectionContext(
        ProtectionCause cause,
        @Nullable Entity actor,
        @Nullable Entity directEntity,
        @Nullable Entity responsibleEntity,
        @Nullable Event nativeEvent,
        java.util.UUID operationId,
        @Nullable org.bukkit.plugin.Plugin producer,
        @Nullable com.aitchn.prism.api.PrismKey behaviorId,
        @Nullable String snapshotId,
        @Nullable java.util.UUID actorId,
        @Nullable java.util.UUID directEntityId,
        @Nullable java.util.UUID responsibleEntityId
) {
    public ProtectionContext(ProtectionCause cause, @Nullable Entity actor, @Nullable Entity directEntity,
                             @Nullable Entity responsibleEntity, @Nullable Event nativeEvent) {
        this(cause, actor, directEntity, responsibleEntity, nativeEvent, java.util.UUID.randomUUID(), null, null, null);
    }

    public ProtectionContext(ProtectionCause cause, @Nullable Entity actor, @Nullable Entity directEntity,
                             @Nullable Entity responsibleEntity, @Nullable Event nativeEvent,
                             java.util.UUID operationId, @Nullable org.bukkit.plugin.Plugin producer,
                             @Nullable com.aitchn.prism.api.PrismKey behaviorId, @Nullable String snapshotId) {
        this(cause, actor, directEntity, responsibleEntity, nativeEvent, operationId, producer, behaviorId,
                snapshotId, actor == null ? null : actor.getUniqueId(),
                directEntity == null ? null : directEntity.getUniqueId(),
                responsibleEntity == null ? null : responsibleEntity.getUniqueId());
    }

    public ProtectionContext {
        Objects.requireNonNull(cause, "cause");
        Objects.requireNonNull(operationId, "operationId");
    }

    public static ProtectionContext unknown() {
        return new ProtectionContext(ProtectionCause.UNKNOWN, null, null, null, null);
    }

    public static ProtectionContext player(Player player) {
        return new ProtectionContext(ProtectionCause.PLAYER_INTERACTION,
                Objects.requireNonNull(player), player, player, null);
    }

    public static ProtectionContext automation() {
        return new ProtectionContext(ProtectionCause.AUTOMATION, null, null, null, null);
    }

    public ProtectionContext withEvent(Event event) {
        return new ProtectionContext(cause, actor, directEntity, responsibleEntity, event,
                operationId, producer, behaviorId, snapshotId, actorId, directEntityId, responsibleEntityId);
    }

    public ProtectionContext withProducer(org.bukkit.plugin.Plugin producer,
                                          @Nullable com.aitchn.prism.api.PrismKey behaviorId,
                                          @Nullable String snapshotId) {
        return new ProtectionContext(cause, actor, directEntity, responsibleEntity, nativeEvent,
                operationId, Objects.requireNonNull(producer), behaviorId, snapshotId, actorId, directEntityId, responsibleEntityId);
    }

    /** Capture on the owner before dispatching profile work. Contains no live Bukkit entity/event references. */
    public ProtectionContext withoutLiveReferences() {
        return new ProtectionContext(cause, null, null, null, null, operationId, producer, behaviorId,
                snapshotId, actorId, directEntityId, responsibleEntityId);
    }

    public @Nullable Player player() {
        return actor instanceof Player player ? player
                : responsibleEntity instanceof Player player ? player : null;
    }
}
