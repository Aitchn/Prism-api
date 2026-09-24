package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.block.Block;

public record BlockTick(
        PrismKey blockId,
        BehaviorOptions options,
        Block block
) implements BlockBehaviorContext {
    public BlockTick {
        Objects.requireNonNull(blockId, "blockId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(block, "block");
    }
}
