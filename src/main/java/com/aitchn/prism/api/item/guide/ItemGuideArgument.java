package com.aitchn.prism.api.item.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/** An immutable argument used inside a translated item information line. */
public sealed interface ItemGuideArgument permits ItemGuideArgument.Literal, ItemGuideArgument.RegistryText {
    record Literal(String value) implements ItemGuideArgument {
        public Literal {
            Objects.requireNonNull(value, "value");
        }
    }

    record RegistryText(PrismKey key) implements ItemGuideArgument {
        public RegistryText {
            Objects.requireNonNull(key, "key");
        }
    }
}
