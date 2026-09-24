package com.aitchn.prism.api.machine;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.item.ItemInstanceState;

public record MachineProcessItem(PrismKey item, int amount, ItemInstanceState state) {
    public MachineProcessItem {
        java.util.Objects.requireNonNull(state, "state");
        if (amount < 1) {
            throw new IllegalArgumentException("A machine process item amount must be positive");
        }
    }

    public MachineProcessItem(PrismKey item, int amount) {
        this(item, amount, ItemInstanceState.empty());
    }
}
