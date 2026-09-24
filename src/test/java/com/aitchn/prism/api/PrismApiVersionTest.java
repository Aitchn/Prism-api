package com.aitchn.prism.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

class PrismApiVersionTest {
    @Test
    void supportsOnlyPublishedNonnegativeApiThreeVersions() {
        PrismApi api = mock(PrismApi.class, CALLS_REAL_METHODS);
        assertEquals(3, PrismApi.API_MAJOR_VERSION);
        assertEquals(23, PrismApi.API_MINOR_VERSION);
        assertTrue(api.supports(3, 0));
        assertFalse(api.supports(3, -1));
        assertTrue(api.supports(3, 1));
        assertTrue(api.supports(3, 2));
        assertTrue(api.supports(3, 3));
        assertTrue(api.supports(3, 4));
        assertTrue(api.supports(3, 5));
        assertTrue(api.supports(3, 6));
        assertTrue(api.supports(3, 7));
        assertTrue(api.supports(3, 8));
        assertTrue(api.supports(3, 9));
        assertTrue(api.supports(3, 10));
        assertTrue(api.supports(3, 11));
        assertTrue(api.supports(3, 12));
        assertTrue(api.supports(3, 13));
        assertTrue(api.supports(3, 14));
        assertTrue(api.supports(3, 15));
        assertTrue(api.supports(3, 16));
        assertTrue(api.supports(3, 17));
        assertTrue(api.supports(3, 18));
        assertTrue(api.supports(3, 19));
        assertTrue(api.supports(3, 20));
        assertTrue(api.supports(3, 21));
        assertTrue(api.supports(3, 22));
        assertTrue(api.supports(3, 23));
        assertFalse(api.supports(3, 24));
        assertFalse(api.supports(2, 10));
        assertFalse(api.supports(4, 0));
    }
}
