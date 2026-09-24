package com.aitchn.prism.api.component;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

public record ComponentValidationContext(
        PrismKey contentId,
        ComponentTarget target,
        ComponentOptions options
) {
    public ComponentValidationContext {
        Objects.requireNonNull(contentId, "contentId");
        Objects.requireNonNull(target, "target");
        options = options == null ? ComponentOptions.empty() : options;
    }
}
