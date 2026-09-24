package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ItemBehaviorContext {
    PrismKey itemId();

    BehaviorOptions options();

    Player player();

    ItemStack item();
}
