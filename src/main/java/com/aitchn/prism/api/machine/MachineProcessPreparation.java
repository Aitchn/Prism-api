package com.aitchn.prism.api.machine;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.List;

public record MachineProcessPreparation(PrismKey recipe, BehaviorOptions options,
                                        List<MachineProcessStack> inputs, List<MachineProcessItem> outputs,
                                        com.aitchn.prism.api.registry.RegistryReadView registry) {
    public MachineProcessPreparation {
        java.util.Objects.requireNonNull(recipe, "recipe");
        java.util.Objects.requireNonNull(options, "options");
        inputs = List.copyOf(inputs);
        outputs = List.copyOf(outputs);
    }

    public MachineProcessPreparation(PrismKey recipe, BehaviorOptions options, List<MachineProcessStack> inputs, List<MachineProcessItem> outputs) {
        this(recipe, options, inputs, outputs, null);
    }
}
