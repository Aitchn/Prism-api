package com.aitchn.prism.api.component;

import com.aitchn.prism.api.data.OptionValues;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public record ComponentOptions(Map<String, Object> values) {
    private static final ComponentOptions EMPTY = new ComponentOptions(Map.of());

    public ComponentOptions {
        Objects.requireNonNull(values, "values");
        values = OptionValues.immutableMap(values);
    }

    public static ComponentOptions empty() {
        return EMPTY;
    }

    public Optional<Object> value(String key) {
        return Optional.ofNullable(values.get(Objects.requireNonNull(key, "key")));
    }

    public String requireString(String key) {
        Object value = values.get(key);
        if (!(value instanceof String string)) {
            throw new IllegalArgumentException("Component option '" + key + "' must be a string");
        }
        return string;
    }

    public int requireInt(String key) {
        return OptionValues.requireInt(values.get(key), key);
    }

    public double requireDouble(String key) {
        return OptionValues.requireDouble(values.get(key), key);
    }

    public boolean requireBoolean(String key) {
        Object value = values.get(key);
        if (!(value instanceof Boolean bool)) {
            throw new IllegalArgumentException("Component option '" + key + "' must be a boolean");
        }
        return bool;
    }

}
