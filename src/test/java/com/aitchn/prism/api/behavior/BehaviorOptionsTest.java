package com.aitchn.prism.api.behavior;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BehaviorOptionsTest {
    @Test
    void recursivelyCopiesAndFreezesInput() {
        List<Object> nested = new ArrayList<>(List.of("first"));
        Map<String, Object> source = new LinkedHashMap<>();
        source.put("nested", nested);
        BehaviorOptions options = new BehaviorOptions(source);

        nested.add("second");
        source.put("late", true);

        assertEquals(List.of("first"), options.values().get("nested"));
        assertThrows(UnsupportedOperationException.class, () -> options.values().put("other", 1));
        assertThrows(UnsupportedOperationException.class,
                () -> ((List<Object>) options.values().get("nested")).add("other"));
    }

    @Test
    void rejectsUnsupportedOrNullValues() {
        assertThrows(IllegalArgumentException.class, () -> new BehaviorOptions(Map.of("bad", new Object())));
        Map<String, Object> nullValue = new LinkedHashMap<>();
        nullValue.put("bad", null);
        assertThrows(IllegalArgumentException.class, () -> new BehaviorOptions(nullValue));
    }
}
