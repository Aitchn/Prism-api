package com.aitchn.prism.api.machine;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.List;
import java.util.Map;

public record MachineProcessValidationContext(
        PrismKey recipeId,
        PrismKey machineId,
        int operatingTemperature,
        List<MachineProcessIngredient> inputs,
        List<MachineProcessIngredient> catalysts,
        List<MachineProcessItem> outputs,
        List<MachineProcessSubstance> substanceInputs,
        List<MachineProcessSubstance> substanceOutputs,
        int duration,
        Map<String, Long> resourcesPerTick,
        BehaviorOptions options,
        com.aitchn.prism.api.registry.RegistryReadView registry,
        Map<String, Long> resourceOutputs,
        com.aitchn.prism.api.machine.MachineResourceOutputMode resourceOutputMode
) {
    public MachineProcessValidationContext {
        inputs = List.copyOf(inputs);
        catalysts = List.copyOf(catalysts);
        outputs = List.copyOf(outputs);
        substanceInputs = List.copyOf(substanceInputs);
        substanceOutputs = List.copyOf(substanceOutputs);
        resourcesPerTick = Map.copyOf(resourcesPerTick);
        resourceOutputs = Map.copyOf(resourceOutputs);
        java.util.Objects.requireNonNull(resourceOutputMode, "resourceOutputMode");
        if (resourceOutputMode == com.aitchn.prism.api.machine.MachineResourceOutputMode.CONTINUOUS && resourceOutputs.isEmpty()) {
            throw new IllegalArgumentException("Continuous production requires scalar outputs");
        }
    }

    public MachineProcessValidationContext(PrismKey recipeId, PrismKey machineId, int operatingTemperature,
            List<MachineProcessIngredient> inputs, List<MachineProcessIngredient> catalysts, List<MachineProcessItem> outputs,
            List<MachineProcessSubstance> substanceInputs, List<MachineProcessSubstance> substanceOutputs, int duration,
            Map<String, Long> resourcesPerTick, BehaviorOptions options, com.aitchn.prism.api.registry.RegistryReadView registry,
            Map<String, Long> resourceOutputs) {
        this(recipeId, machineId, operatingTemperature, inputs, catalysts, outputs, substanceInputs, substanceOutputs,
                duration, resourcesPerTick, options, registry, resourceOutputs, MachineResourceOutputMode.COMPLETION);
    }

    public MachineProcessValidationContext(PrismKey recipeId, PrismKey machineId, int operatingTemperature,
            List<MachineProcessIngredient> inputs, List<MachineProcessIngredient> catalysts, List<MachineProcessItem> outputs,
            List<MachineProcessSubstance> substanceInputs, List<MachineProcessSubstance> substanceOutputs, int duration,
            Map<String, Long> resourcesPerTick, BehaviorOptions options, com.aitchn.prism.api.registry.RegistryReadView registry) {
        this(recipeId, machineId, operatingTemperature, inputs, catalysts, outputs, substanceInputs, substanceOutputs,
                duration, resourcesPerTick, options, registry, Map.of());
    }

    public MachineProcessValidationContext(PrismKey recipeId, PrismKey machineId, int operatingTemperature,
            List<MachineProcessIngredient> inputs, List<MachineProcessIngredient> catalysts, List<MachineProcessItem> outputs,
            List<MachineProcessSubstance> substanceInputs, List<MachineProcessSubstance> substanceOutputs, int duration,
            Map<String, Long> resourcesPerTick, BehaviorOptions options) {
        this(recipeId, machineId, operatingTemperature, inputs, catalysts, outputs, substanceInputs, substanceOutputs,
                duration, resourcesPerTick, options, null);
    }
}
