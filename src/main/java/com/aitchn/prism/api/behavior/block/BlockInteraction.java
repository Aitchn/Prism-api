package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

public record BlockInteraction(
        PrismKey blockId,
        BehaviorOptions options,
        Block block,
        Player player,
        EquipmentSlot hand,
        BlockInteractionAction action
) implements BlockBehaviorContext {
    public BlockInteraction {
        Objects.requireNonNull(blockId, "blockId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(block, "block");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(hand, "hand");
        Objects.requireNonNull(action, "action");
    }
}
