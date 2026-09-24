package com.aitchn.prism.api.item.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;

public record ItemGuideEntry(PrismKey item, List<ItemGuideLine> lines, List<ItemGuideChoice> choices) {
    public ItemGuideEntry {
        Objects.requireNonNull(item, "item");
        lines = List.copyOf(Objects.requireNonNull(lines, "lines"));
        choices = List.copyOf(choices);
        if (choices.size() > 64 || choices.stream().map(ItemGuideChoice::field).distinct().count() != choices.size()) {
            throw new IllegalArgumentException("Duplicate or excessive item Guide choice fields");
        }
    }

    public ItemGuideEntry(PrismKey item, List<ItemGuideLine> lines) {
        this(item, lines, List.of());
    }
}
