package com.aitchn.prism.api.machine;

import com.aitchn.prism.api.item.ItemInstanceState;

/** One declared output's state. A nonnegative source index preserves one consumed item's metadata. */
public record MachineProcessOutput(ItemInstanceState state, int copyInput) {
    public MachineProcessOutput {
        java.util.Objects.requireNonNull(state, "state");
        if (copyInput < -1) {
            throw new IllegalArgumentException("copyInput must be -1 or a reserved input index");
        }
    }

    public MachineProcessOutput(ItemInstanceState state) {
        this(state, -1);
    }
}
