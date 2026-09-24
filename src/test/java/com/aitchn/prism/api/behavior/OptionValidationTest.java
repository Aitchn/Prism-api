package com.aitchn.prism.api.behavior;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.aitchn.prism.api.component.ComponentOptions;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class OptionValidationTest {
    @Test
    void rejectsMutableNumbersNonFiniteNumbersCyclesAndExcessiveNesting() {
        List<Object> cycle = new ArrayList<>();
        cycle.add(cycle);
        Object deep = "leaf";
        for (int i = 0; i < 66; i++) {
            deep = List.of(deep);
        }
        for (Object value : List.of(new AtomicInteger(3), Double.NaN, Double.POSITIVE_INFINITY, cycle, deep)) {
            assertThrows(IllegalArgumentException.class, () -> new BehaviorOptions(Map.of("value", value)));
            assertThrows(IllegalArgumentException.class, () -> new ComponentOptions(Map.of("value", value)));
        }
    }

    @Test
    void integerReadsRejectTruncationAndOverflowAcrossBothOptionFamilies() {
        for (Number value : List.of(1.5, Long.MAX_VALUE, new BigInteger("4294967296"), new BigDecimal("0.1"))) {
            assertThrows(IllegalArgumentException.class,
                    () -> new BehaviorOptions(Map.of("value", value)).requireInt("value"));
            assertThrows(IllegalArgumentException.class,
                    () -> new ComponentOptions(Map.of("value", value)).requireInt("value"));
        }
        assertEquals(3, new BehaviorOptions(Map.of("value", 3.0)).requireInt("value"));
        assertEquals(Integer.MIN_VALUE,
                new ComponentOptions(Map.of("value", (long) Integer.MIN_VALUE)).requireInt("value"));
    }

    @Test
    void sharedAcyclicCollectionsAndImmutableLargeNumbersRemainSupported() {
        List<Object> shared = new ArrayList<>(List.of(new BigInteger("999999999999999999999999")));
        BehaviorOptions options = new BehaviorOptions(Map.of("first", shared, "second", shared));
        shared.clear();
        assertEquals(options.values().get("first"), options.values().get("second"));
        assertEquals(1, ((List<?>) options.values().get("first")).size());
        assertThrows(IllegalArgumentException.class,
                () -> new ComponentOptions(Map.of("value", new BigDecimal("1e1000"))).requireDouble("value"));
    }
}
