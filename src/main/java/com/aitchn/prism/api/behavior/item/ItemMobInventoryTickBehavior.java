package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.behavior.BehaviorResult;
import java.util.Set;
import org.bukkit.entity.EntityType;

/** Item capability invoked for custom stacks held by supported inventory mobs. */
public interface ItemMobInventoryTickBehavior extends ItemBehaviorHandler {
    /** Entity types for which this capability can run. */
    Set<EntityType> entityTypes();

    BehaviorResult inventoryTick(ItemMobInventoryTick context);
}
