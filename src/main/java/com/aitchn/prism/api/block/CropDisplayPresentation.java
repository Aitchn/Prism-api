package com.aitchn.prism.api.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.component.ComponentDefinition;
import com.aitchn.prism.api.component.ComponentOptions;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Immutable model selection for the eight native wheat ages. Models do not define gameplay state. */
public record CropDisplayPresentation(List<PrismKey> models) {
    public static final PrismKey TYPE = PrismKey.parse("prism:crop_display");

    public CropDisplayPresentation {
        models = List.copyOf(models);
        if (models.size() != 8) {
            throw new IllegalArgumentException("Crop display requires exactly eight age models");
        }
        for (PrismKey model : models) {
            Objects.requireNonNull(model, "model");
            if (model.namespace().equals("minecraft")) {
                throw new IllegalArgumentException("Custom crop models must be author-owned resources");
            }
        }
    }

    public static CropDisplayPresentation from(ComponentOptions options) {
        if (!options.values().keySet().equals(Set.of("models"))
                || !(options.values().get("models") instanceof List<?> values)) {
            throw new IllegalArgumentException("Crop display requires only a models list");
        }
        return new CropDisplayPresentation(values.stream().map(value -> {
            if (!(value instanceof String text)) {
                throw new IllegalArgumentException("Crop display model must be a namespaced resource key");
            }
            return PrismKey.parse(text);
        }).toList());
    }

    public static Optional<CropDisplayPresentation> find(List<ComponentDefinition> components) {
        return components.stream().filter(value -> value.type().equals(TYPE))
                .findFirst().map(value -> from(value.options()));
    }

    public PrismKey model(int age) {
        if (age < 0 || age > 7) throw new IllegalArgumentException("Wheat age must be from zero to seven");
        return models.get(age);
    }
}
