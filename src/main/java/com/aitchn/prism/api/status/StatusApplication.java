package com.aitchn.prism.api.status;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/** One source's online-only effect request. Levels are one-based; duration uses player scheduler ticks. */
public record StatusApplication(PrismKey type, PrismKey source, int level, int durationTicks) {
    public StatusApplication {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(source, "source");
        if (level < 1 || durationTicks < 1 || durationTicks > 72000) {
            throw new IllegalArgumentException("Status level must be positive and duration 1..72000 ticks");
        }
    }
}
