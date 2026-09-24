package com.aitchn.prism.api.protection;

import java.util.UUID;
import org.bukkit.block.Block;

public record BlockPosition(UUID worldId, int x, int y, int z) {
    public static BlockPosition of(Block block) {
        return new BlockPosition(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
    }
}
