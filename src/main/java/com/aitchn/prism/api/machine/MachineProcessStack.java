package com.aitchn.prism.api.machine;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.item.ItemInstanceState;
import java.util.Objects;

/** Immutable facts about one reserved input stack; no inventory or world object is exposed. */
public record MachineProcessStack(PrismKey item, int amount, ItemInstanceState state, int damage) {
    public MachineProcessStack {
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(state, "state");
        if (amount < 1 || damage < 0) {
            throw new IllegalArgumentException("Invalid machine process stack");
        }
    }
}
