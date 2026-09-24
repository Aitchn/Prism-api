package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public record ItemEntityInteraction(
        PrismKey itemId,
        BehaviorOptions options,
        Player player,
        ItemStack item,
        EquipmentSlot hand,
        Entity target
) implements ItemBehaviorContext {
    public ItemEntityInteraction {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(item, "item");
        Objects.requireNonNull(hand, "hand");
        Objects.requireNonNull(target, "target");
    }
}
