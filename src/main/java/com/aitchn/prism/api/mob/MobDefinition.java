package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorDefinition;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public record MobDefinition(
        PrismKey id,
        PrismKey baseEntity,
        String displayNameKey,
        double scale,
        Map<PrismKey, Double> attributes,
        MobControllerDefinition controller,
        List<BehaviorDefinition> configuredBehaviors
) {
    public MobDefinition {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(baseEntity, "baseEntity");
        if (displayNameKey == null || displayNameKey.isBlank()) {
            throw new IllegalArgumentException("Mob display name keys must be non-blank");
        }
        if (!Double.isFinite(scale) || scale <= 0.0D) {
            throw new IllegalArgumentException("Mob scale must be a finite positive number");
        }
        attributes = Map.copyOf(Objects.requireNonNull(attributes, "attributes"));
        attributes.forEach((attribute, value) -> {
            Objects.requireNonNull(attribute, "Mob attribute IDs cannot be null");
            if (value == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Mob attribute values must be finite numbers");
            }
        });
        Objects.requireNonNull(controller, "controller");
        configuredBehaviors = List.copyOf(Objects.requireNonNull(configuredBehaviors, "configuredBehaviors"));
    }
}
