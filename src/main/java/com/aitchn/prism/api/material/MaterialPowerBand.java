package com.aitchn.prism.api.material;

import java.util.Locale;

public enum MaterialPowerBand {
    EARLY,
    MID,
    ADVANCED;

    public static MaterialPowerBand parse(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Material power bands must be non-blank");
        }
        try {
            return valueOf(value.toUpperCase(Locale.ROOT).replace('-', '_'));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unsupported material power band: " + value, exception);
        }
    }
}
