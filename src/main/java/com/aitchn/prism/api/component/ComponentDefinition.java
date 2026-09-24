package com.aitchn.prism.api.component;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

public record ComponentDefinition(
        PrismKey type,
        ComponentFacet facet,
        ComponentOptions options
) {
    public ComponentDefinition {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(facet, "facet");
        options = options == null ? ComponentOptions.empty() : options;
    }
}
