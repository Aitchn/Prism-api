package com.aitchn.prism.api.block;

import static org.junit.jupiter.api.Assertions.*;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BlockOrientationTest {
    @Test
    void everyOrthogonalOrientationMapsAllSixWorldFacesExactlyOnce() {
        for (BlockDirection front : BlockDirection.values()) {
            for (BlockDirection top : BlockDirection.values()) {
                if (top == front || top == front.opposite()) continue;
                var orientation = new BlockOrientation(front, top);
                var seen = new HashSet<BlockDirection>();
                for (BlockSide side : BlockSide.values()) {
                    assertTrue(seen.add(orientation.world(side)));
                    assertEquals(side, orientation.local(orientation.world(side)));
                }
                assertEquals(6, seen.size());
            }
        }
        assertThrows(IllegalArgumentException.class, () -> new BlockOrientation(BlockDirection.UP, BlockDirection.DOWN));
    }

    @Test
    void aRotatedFrontInputAndRightOutputKeepTheirMachineMeaning() {
        var port = new BlockPortConfiguration("process", BlockPortKind.SUBSTANCE, BlockPortMode.BOTH,
                Map.of(BlockSide.FRONT, BlockPortMode.INPUT, BlockSide.RIGHT, BlockPortMode.OUTPUT));
        var orientation = BlockOrientation.facing(BlockDirection.EAST);
        assertEquals(BlockPortMode.INPUT, port.mode(BlockDirection.EAST, orientation));
        assertEquals(BlockPortMode.OUTPUT, port.mode(BlockDirection.SOUTH, orientation));
        assertEquals(BlockPortMode.NONE, port.mode(BlockDirection.NORTH, orientation));
        assertEquals(BlockPortMode.NONE, port.mode(BlockDirection.UP, orientation));
    }

    @Test
    void configurationsAreCapturedAndCannotExposeAnInputBufferAsOutput() {
        var supplied = new EnumMap<BlockSide, BlockPortMode>(BlockSide.class);
        supplied.put(BlockSide.TOP, BlockPortMode.INPUT);
        var port = new BlockPortConfiguration("feed", BlockPortKind.ITEM, BlockPortMode.INPUT, supplied);
        supplied.clear();
        assertEquals(BlockPortMode.INPUT, port.sides().get(BlockSide.TOP));
        assertThrows(UnsupportedOperationException.class, () -> port.sides().clear());
        assertThrows(IllegalArgumentException.class, () -> port.with(BlockSide.TOP, BlockPortMode.BOTH));
        assertEquals(BlockPortMode.NONE, port.with(BlockSide.TOP, BlockPortMode.NONE).sides().get(BlockSide.TOP));
        assertEquals(BlockPortMode.INPUT, port.sides().get(BlockSide.TOP));
    }
}
