package com.aitchn.prism.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PrismKeyTest {
    @Test
    void parsesNamespacedKey() {
        PrismKey key = PrismKey.parse("example:machine.behavior");

        assertEquals("example", key.namespace());
        assertEquals("machine.behavior", key.value());
        assertEquals("example:machine.behavior", key.toString());
    }

    @Test
    void rejectsMissingNamespaceAndUppercaseValues() {
        assertThrows(IllegalArgumentException.class, () -> PrismKey.parse("machine"));
        assertThrows(IllegalArgumentException.class, () -> PrismKey.parse("Example:machine"));
    }
}
