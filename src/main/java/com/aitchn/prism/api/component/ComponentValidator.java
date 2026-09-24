package com.aitchn.prism.api.component;

@FunctionalInterface
public interface ComponentValidator {
    void validate(ComponentValidationContext context);

    static ComponentValidator acceptingAnyOptions() {
        return context -> {
        };
    }
}
