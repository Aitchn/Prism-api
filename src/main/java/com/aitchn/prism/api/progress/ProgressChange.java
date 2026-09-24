package com.aitchn.prism.api.progress;

import com.aitchn.prism.api.PrismKey;
import org.jetbrains.annotations.Nullable;

public record ProgressChange(
        Type type,
        PlayerProgressView before,
        PlayerProgressView after,
        @Nullable PrismKey subject
) {
    public enum Type {
        RESEARCH_COMPLETED,
        RESEARCH_REVOKED,
        COUNTER_INCREMENTED
    }
}
