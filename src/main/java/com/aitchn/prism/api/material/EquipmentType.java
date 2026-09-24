package com.aitchn.prism.api.material;

import java.util.Locale;

public enum EquipmentType {
    TOOL,
    WEAPON,
    ARMOR,
    UTILITY;

    public static EquipmentType parse(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Equipment types must be non-blank");
        }
        try {
            return valueOf(value.toUpperCase(Locale.ROOT).replace('-', '_'));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unsupported equipment type: " + value, exception);
        }
    }
}
