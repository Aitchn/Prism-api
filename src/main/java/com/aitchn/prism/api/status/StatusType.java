package com.aitchn.prism.api.status;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/** Immutable addon-defined type. Text keys are interpreted by the chosen presentation provider. */
public record StatusType(PrismKey id, String nameKey, String descriptionKey, int maxLevel, int intervalTicks) {
    public StatusType {
        Objects.requireNonNull(id, "id");
        if (nameKey == null || nameKey.isBlank() || descriptionKey == null || descriptionKey.isBlank()) {
            throw new IllegalArgumentException("Status text keys must not be blank");
        }
        if (maxLevel < 1 || maxLevel > 100 || intervalTicks < 1 || intervalTicks > 1200) {
            throw new IllegalArgumentException("Status level must be 1..100 and interval 1..1200 ticks");
        }
    }
}
