package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.behavior.BehaviorPhase;
import java.util.Objects;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public record ItemBlockBreak(
        PrismKey itemId,
        BehaviorOptions options,
        Player player,
        ItemStack item,
        EquipmentSlot hand,
        Block block,
        BehaviorPhase phase
) implements ItemBehaviorContext {
    public ItemBlockBreak {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(hand, "hand");
        Objects.requireNonNull(block, "block");
        Objects.requireNonNull(phase, "phase");
    }
}
