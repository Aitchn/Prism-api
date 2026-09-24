package com.aitchn.prism.api.feedback;

import com.aitchn.prism.api.block.BlockOrientation;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Snapshot captured on the emitter's owning region. Anchors are world coordinates. */
public record FeedbackOrigin(UUID world, String instance, FeedbackVector center, BlockOrientation orientation,
                             Map<String, FeedbackVector> anchors) {
    public FeedbackOrigin {
        Objects.requireNonNull(world); Objects.requireNonNull(center); Objects.requireNonNull(orientation);
        if (instance == null || instance.isBlank()) throw new IllegalArgumentException("Feedback instance is required");
        anchors = Map.copyOf(anchors);
    }
}
