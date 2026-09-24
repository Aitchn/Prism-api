package com.aitchn.prism.api.behavior.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

final class DamageAdjustmentTest {
    @Test
    void appliesMultiplierThenAdditiveWithoutProducingNegativeDamage() {
        assertEquals(9.0D, new DamageAdjustment(0.8D, 1.0D).apply(10.0D));
        assertEquals(0.0D, new DamageAdjustment(0.0D, -5.0D).apply(10.0D));
    }

    @Test
    void rejectsUnsafeNumbers() {
        assertThrows(IllegalArgumentException.class, () -> new DamageAdjustment(-1.0D, 0.0D));
        assertThrows(IllegalArgumentException.class, () -> new DamageAdjustment(Double.NaN, 0.0D));
    }
}
