package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.Objects;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public record ItemConsumption(
        PrismKey itemId,
        BehaviorOptions options,
        Player player,
        ItemStack item
) implements ItemBehaviorContext {
    public ItemConsumption {
        Objects.requireNonNull(itemId, "itemId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(item, "item");
    }
}
