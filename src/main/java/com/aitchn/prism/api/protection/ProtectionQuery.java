package com.aitchn.prism.api.protection;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public record ProtectionQuery(
        ProtectionAction action,
        @Nullable Player player,
        List<BlockPosition> targets,
        @Nullable PrismKey contentId
) {
    public ProtectionQuery {
        targets = List.copyOf(targets);
        if (targets.isEmpty()) {
            throw new IllegalArgumentException("Protection queries require at least one target");
        }
    }
}
