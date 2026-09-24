package com.aitchn.prism.api;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record PrismKey(String namespace, String value) {
    private static final Pattern PART = Pattern.compile("[a-z0-9._-]+");

    public PrismKey {
        namespace = validate(namespace, "namespace");
        value = validate(value, "value");
    }

    public static PrismKey parse(String value) {
        Objects.requireNonNull(value, "value");
        int separator = value.indexOf(':');
        if (separator <= 0 || separator == value.length() - 1 || value.indexOf(':', separator + 1) >= 0) {
            throw new IllegalArgumentException("Prism keys must use the <namespace>:<value> format: " + value);
        }
        return new PrismKey(value.substring(0, separator), value.substring(separator + 1));
    }

    private static String validate(String value, String part) {
        Objects.requireNonNull(value, part);
        String normalized = value.toLowerCase(Locale.ROOT);
        if (!value.equals(normalized) || !PART.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid Prism key " + part + ": " + value);
        }
        return value;
    }

    @Override
    public String toString() {
        return namespace + ':' + value;
    }
}
