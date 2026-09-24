package com.aitchn.prism.api.item;

import com.aitchn.prism.api.PrismKey;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/** Immutable, namespaced instance values; each field must also be declared by the item. */
public record ItemInstanceState(Map<PrismKey, String> values) {
    public ItemInstanceState {
        values = Map.copyOf(Objects.requireNonNull(values, "values"));
        if (values.size() > 64 || values.values().stream().anyMatch(value -> value.length() > 4096)) {
            throw new IllegalArgumentException("Item state supports at most 64 fields of 4096 characters each");
        }
    }

    public static ItemInstanceState empty() {
        return new ItemInstanceState(Map.of());
    }

    public Optional<String> value(PrismKey key) {
        return Optional.ofNullable(values.get(Objects.requireNonNull(key, "key")));
    }

    public ItemInstanceState with(PrismKey key, String value) {
        Map<PrismKey, String> updated = new LinkedHashMap<>(values);
        updated.put(Objects.requireNonNull(key, "key"), Objects.requireNonNull(value, "value"));
        return new ItemInstanceState(updated);
    }
}
