package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.behavior.BehaviorResult;
import java.util.Set;
import org.bukkit.entity.EntityType;

/** Item capability that authorizes an inventory mob to pick up a custom item. */
public interface ItemEntityPickupBehavior extends ItemBehaviorHandler {
    /** Entity types for which this capability can run. */
    Set<EntityType> entityTypes();

    BehaviorResult pickup(ItemEntityPickup context);
}
