package com.aitchn.prism.api.behavior;

import java.util.Map;

@FunctionalInterface
public interface StructureBehaviorValidator {
    void validate(Map<String, Object> options);

    static StructureBehaviorValidator acceptingAnyOptions() {
        return options -> {
        };
    }
}
