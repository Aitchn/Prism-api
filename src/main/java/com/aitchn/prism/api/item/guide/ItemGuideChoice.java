package com.aitchn.prism.api.item.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;

/** Reference-only instance field selector. Preview items are never granted to the viewer. */
public record ItemGuideChoice(PrismKey field, PrismKey text, PrismKey previewItem, PrismKey previewField,
                              List<Value> values) {
    public ItemGuideChoice {
        Objects.requireNonNull(field, "field");
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(previewItem, "previewItem");
        Objects.requireNonNull(previewField, "previewField");
        values = List.copyOf(values);
        if (values.isEmpty() || values.size() > 256 || values.stream().map(Value::value).distinct().count() != values.size()) {
            throw new IllegalArgumentException("Guide choices require 1 through 256 distinct values");
        }
    }

    /** Optional, validated reference details shown only by the Guide's per-icon lore pager. */
    public record Value(String value, PrismKey text, List<ItemGuideLine> details) {
        public Value {
            Objects.requireNonNull(value, "value");
            Objects.requireNonNull(text, "text");
            details = List.copyOf(details);
            if (details.size() > 64 || details.stream().map(ItemGuideLine::id).distinct().count() != details.size()) {
                throw new IllegalArgumentException("Guide value details require at most 64 distinct lines");
            }
            if (value.length() > 4096) {
                throw new IllegalArgumentException("Guide state value is too long");
            }
        }

        public Value(String value, PrismKey text) {
            this(value, text, List.of());
        }
    }
}
