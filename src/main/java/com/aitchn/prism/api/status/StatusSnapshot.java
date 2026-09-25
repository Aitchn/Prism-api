package com.aitchn.prism.api.status;

import com.aitchn.prism.api.PrismKey;
import java.util.UUID;

/** Detached immutable observation; retaining it does not keep an effect active. */
public record StatusSnapshot(UUID instanceId, UUID playerId, StatusType type, PrismKey source,
                             int level, int remainingTicks, long elapsedTicks) {
}
