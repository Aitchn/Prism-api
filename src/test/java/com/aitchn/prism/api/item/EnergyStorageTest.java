package com.aitchn.prism.api.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.component.ComponentOptions;
import java.util.Map;
import org.junit.jupiter.api.Test;

class EnergyStorageTest {
    @Test
    void parsesResourceCapacityAndOptionalUseCost() {
        assertEquals(new EnergyStorage(PrismKey.parse("prism:steam"), 4000L, 10L), EnergyStorage.from(new ComponentOptions(Map.of(
                "resource", "prism:steam", "capacity", 4000, "use-cost", 10))));
        assertEquals(0L, EnergyStorage.from(new ComponentOptions(Map.of(
                "resource", "prism:power", "capacity", 1))).useCost());
    }

    @Test
    void rejectsMissingUnknownOrOutOfRangeOptions() {
        assertThrows(IllegalArgumentException.class, () -> EnergyStorage.from(new ComponentOptions(Map.of("capacity", 10))));
        assertThrows(IllegalArgumentException.class, () -> EnergyStorage.from(new ComponentOptions(Map.of("resource", "prism:steam"))));
        assertThrows(IllegalArgumentException.class, () -> EnergyStorage.from(new ComponentOptions(Map.of(
                "resource", "prism:steam", "capacity", 0))));
        assertThrows(IllegalArgumentException.class, () -> EnergyStorage.from(new ComponentOptions(Map.of(
                "resource", "prism:steam", "capacity", 10, "use-cost", 11))));
        assertThrows(IllegalArgumentException.class, () -> EnergyStorage.from(new ComponentOptions(Map.of(
                "resource", "prism:steam", "capacity", 10, "stored", 5))));
    }
}
