package com.aitchn.prism.api.data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** The shared, server-independent scalar and collection contract for Prism option maps. */
public final class OptionValues {
    public static final int MAX_NESTING_DEPTH = 64;
    private static final Set<Class<?>> NUMBERS = Set.of(
            Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class,
            BigInteger.class, BigDecimal.class
    );

    private OptionValues() {
    }

    public static Map<String, Object> immutableMap(Map<?, ?> source) {
        return map(Objects.requireNonNull(source, "source"), new IdentityHashMap<>(), 0);
    }

    /** Rejects fractional and out-of-range values instead of truncating or wrapping them. */
    public static int requireInt(Object value, String key) {
        if (!(value instanceof Number number) || !NUMBERS.contains(value.getClass())) {
            throw new IllegalArgumentException("Option '" + key + "' must be an integer");
        }
        try {
            return new BigDecimal(number.toString()).intValueExact();
        } catch (NumberFormatException | ArithmeticException exception) {
            throw new IllegalArgumentException("Option '" + key + "' must be an exact 32-bit integer", exception);
        }
    }

    public static double requireDouble(Object value, String key) {
        if (!(value instanceof Number number) || !NUMBERS.contains(value.getClass())
                || !Double.isFinite(number.doubleValue())) {
            throw new IllegalArgumentException("Option '" + key + "' must be a finite number");
        }
        return number.doubleValue();
    }

    private static Map<String, Object> map(Map<?, ?> source, IdentityHashMap<Object, Boolean> ancestors, int depth) {
        enter(source, ancestors, depth);
        try {
            Map<String, Object> result = new LinkedHashMap<>();
            source.forEach((key, value) -> {
                if (!(key instanceof String string) || string.isBlank()) {
                    throw new IllegalArgumentException("Option keys must be non-blank strings");
                }
                result.put(string, value(value, ancestors, depth + 1));
            });
            return Map.copyOf(result);
        } finally {
            ancestors.remove(source);
        }
    }

    private static Object value(Object value, IdentityHashMap<Object, Boolean> ancestors, int depth) {
        if (value instanceof String || value instanceof Boolean) {
            return value;
        }
        if (value instanceof Number number && NUMBERS.contains(value.getClass())) {
            if ((number instanceof Double || number instanceof Float) && !Double.isFinite(number.doubleValue())) {
                throw new IllegalArgumentException("Option numbers must be finite");
            }
            return value;
        }
        if (value instanceof Map<?, ?> nested) {
            return map(nested, ancestors, depth);
        }
        if (value instanceof List<?> list) {
            enter(list, ancestors, depth);
            try {
                List<Object> result = new ArrayList<>(list.size());
                list.forEach(child -> result.add(value(child, ancestors, depth + 1)));
                return List.copyOf(result);
            } finally {
                ancestors.remove(list);
            }
        }
        throw new IllegalArgumentException("Unsupported option value: "
                + (value == null ? "null" : value.getClass().getName()));
    }

    private static void enter(Object container, IdentityHashMap<Object, Boolean> ancestors, int depth) {
        if (depth > MAX_NESTING_DEPTH) {
            throw new IllegalArgumentException("Option nesting exceeds " + MAX_NESTING_DEPTH);
        }
        if (ancestors.put(container, Boolean.TRUE) != null) {
            throw new IllegalArgumentException("Option collections cannot contain cycles");
        }
    }
}
