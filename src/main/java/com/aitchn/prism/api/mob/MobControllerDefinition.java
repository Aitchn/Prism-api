package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;

public record MobControllerDefinition(PrismKey type, BehaviorOptions options) {
    public MobControllerDefinition {
        Objects.requireNonNull(type, "type");
        options = options == null ? BehaviorOptions.empty() : options;
    }
}
