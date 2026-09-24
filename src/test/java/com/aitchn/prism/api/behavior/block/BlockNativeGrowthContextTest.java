package com.aitchn.prism.api.behavior.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.behavior.BehaviorPhase;
import java.util.Map;
import org.bukkit.block.Block;
import org.junit.jupiter.api.Test;

final class BlockNativeGrowthContextTest {
    @Test
    void capturesPreviousAndNextNativePropertiesBeforeCommit() {
        BlockNativeGrowthContext context = new BlockNativeGrowthContext(
                PrismKey.parse("prism:tomato"),
                BehaviorOptions.empty(),
                mock(Block.class),
                "minecraft:wheat[age=2]",
                "minecraft:wheat[age=3]",
                BehaviorPhase.PREPARE
        );

        assertEquals("2", context.previousProperty("age"));
        assertEquals("3", context.nextProperty("age"));
        assertEquals(Map.of("age", "2"), context.previousProperties());
        assertEquals("minecraft:wheat[age=3]", context.newState());
        assertThrows(UnsupportedOperationException.class,
                () -> context.nextProperties().put("age", "7"));
    }
}
