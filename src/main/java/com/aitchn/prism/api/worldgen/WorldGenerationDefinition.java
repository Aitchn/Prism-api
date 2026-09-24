package com.aitchn.prism.api.worldgen;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;

public record WorldGenerationDefinition(
        PrismKey id,
        PrismKey type,
        BehaviorOptions options
) {
    public WorldGenerationDefinition {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(options, "options");
    }
}
