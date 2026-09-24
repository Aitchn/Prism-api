package com.aitchn.prism.api.behavior;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

public record BehaviorDefinition(String id, PrismKey type, BehaviorOptions options) {
    public BehaviorDefinition {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Behavior binding IDs must be non-blank");
        }
        Objects.requireNonNull(type, "type");
        options = options == null ? BehaviorOptions.empty() : options;
    }

    public BehaviorDefinition(PrismKey type, BehaviorOptions options) {
        this(type.toString(), type, options);
    }
}
