package com.aitchn.prism.api.item.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;

public record ItemGuideLine(
        PrismKey id,
        PrismKey text,
        int order,
        List<ItemGuideArgument> arguments
) {
    public ItemGuideLine {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(text, "text");
        arguments = List.copyOf(Objects.requireNonNull(arguments, "arguments"));
    }

    public ItemGuideLine(PrismKey id, PrismKey text, int order, ItemGuideArgument... arguments) {
        this(id, text, order, List.of(arguments));
    }
}
