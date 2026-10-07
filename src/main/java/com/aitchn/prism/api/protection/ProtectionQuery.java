package com.aitchn.prism.api.protection;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

/** Immutable query shape; referenced Bukkit entities/events still require the appropriate execution owner. */
public record ProtectionQuery(
        ProtectionAction action,
        @Nullable Player player,
        List<BlockPosition> targets,
        @Nullable PrismKey contentId,
        ProtectionContext context,
        List<ProtectionTarget> subjects
) {
    /** Existing descriptor retained. A null player means unknown attribution, never a placing owner. */
    public ProtectionQuery(ProtectionAction action, @Nullable Player player,
                           List<BlockPosition> targets, @Nullable PrismKey contentId) {
        this(action, player, targets, contentId,
                player == null ? ProtectionContext.unknown() : ProtectionContext.player(player),
                targets.stream().map(position -> ProtectionTarget.block(
                        ProtectionTarget.Role.TARGET, position, null)).toList());
    }

    public ProtectionQuery {
        Objects.requireNonNull(action, "action");
        Objects.requireNonNull(context, "context");
        targets = List.copyOf(targets);
        subjects = List.copyOf(subjects);
        if (subjects.isEmpty()) throw new IllegalArgumentException("Protection queries require at least one target");
        if (player != context.player()) throw new IllegalArgumentException("Player must agree with actor attribution");
        List<BlockPosition> positions = subjects.stream().map(ProtectionTarget::block)
                .filter(Objects::nonNull).distinct().toList();
        if (!targets.stream().distinct().toList().equals(positions)) {
            throw new IllegalArgumentException("Legacy targets must include all block subjects");
        }
    }

    public static ProtectionQuery of(ProtectionAction action, ProtectionContext context,
                                     List<ProtectionTarget> subjects, @Nullable PrismKey contentId) {
        return new ProtectionQuery(action, context.player(), subjects.stream().map(ProtectionTarget::block)
                .filter(Objects::nonNull).distinct().toList(), contentId, context, subjects);
    }

    public ProtectionQuery withContext(ProtectionContext context) {
        return of(action, context, subjects, contentId);
    }
}
