package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;

public record MobSpawnConditionDefinition(String id, PrismKey type, BehaviorOptions options) {
    public MobSpawnConditionDefinition {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Mob spawn condition IDs must be non-blank");
        }
        Objects.requireNonNull(type, "type");
        options = options == null ? BehaviorOptions.empty() : options;
    }
}
