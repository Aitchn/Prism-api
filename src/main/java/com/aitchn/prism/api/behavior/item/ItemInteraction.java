package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public record ItemInteraction(
        PrismKey itemId,
        BehaviorOptions options,
        Player player,
        ItemStack item,
        EquipmentSlot hand,
        ItemInteractionAction action,
        Block clickedBlock,
        BlockFace clickedFace,
        com.aitchn.prism.api.registry.RegistryReadView registry,
        java.util.function.BooleanSupplier active
) implements ItemBehaviorContext {
    public ItemInteraction {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(hand, "hand");
        Objects.requireNonNull(action, "action");
        Objects.requireNonNull(active, "active");
        if (action.name().endsWith("BLOCK") && clickedBlock == null) {
            throw new IllegalArgumentException("Block interactions require a clicked block");
        }
    }

    public ItemInteraction(PrismKey itemId, BehaviorOptions options, Player player, ItemStack item,
                           EquipmentSlot hand, ItemInteractionAction action, Block clickedBlock, BlockFace clickedFace) {
        this(itemId, options, player, item, hand, action, clickedBlock, clickedFace, null, () -> true);
    }
}
