package com.aitchn.prism.api.behavior;

/** Which validated solid cells may invoke a structure's registered behavior. */
public enum StructureInteractionTarget {
    CONTROLLER,
    FORMED_MEMBERS;

    public static StructureInteractionTarget parse(String value) {
        return switch (value) {
            case "controller" -> CONTROLLER;
            case "formed-members" -> FORMED_MEMBERS;
            default -> throw new IllegalArgumentException("Unsupported structure interaction target: " + value);
        };
    }
}
