package com.aitchn.prism.api.machine;

import com.aitchn.prism.api.PrismKey;

public record MachineProcessSubstance(String reservoir, PrismKey substance, long amount) {
    public MachineProcessSubstance {
        if (reservoir != null && !reservoir.matches("[a-z0-9._-]+")) {
            throw new IllegalArgumentException("Invalid machine process reservoir reference: " + reservoir);
        }
        if (amount < 1L) {
            throw new IllegalArgumentException("A machine process substance amount must be positive");
        }
    }
}
