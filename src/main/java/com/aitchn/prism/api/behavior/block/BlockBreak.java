package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.behavior.BehaviorPhase;
import java.util.Objects;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public record BlockBreak(
        PrismKey blockId,
        BehaviorOptions options,
        Block block,
        Player player,
        BehaviorPhase phase
) implements BlockBehaviorContext {
    public BlockBreak {
        Objects.requireNonNull(blockId, "blockId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(block, "block");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(phase, "phase");
    }
}
