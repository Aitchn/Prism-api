package com.aitchn.prism.api.machine;

@FunctionalInterface
public interface MachineProcessValidator {
    void validate(MachineProcessValidationContext context);

    static MachineProcessValidator acceptingAnyDefinition() {
        return context -> {
        };
    }
}
