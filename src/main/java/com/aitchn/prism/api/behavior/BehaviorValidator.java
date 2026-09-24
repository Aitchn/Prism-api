package com.aitchn.prism.api.behavior;

@FunctionalInterface
public interface BehaviorValidator {
    void validate(BehaviorOptions options);

    static BehaviorValidator acceptingAnyOptions() {
        return options -> {
        };
    }
}
