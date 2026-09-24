package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorValidator;
import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface ItemBehaviorRegistry {
    default void register(Plugin owner, PrismKey id, ItemBehaviorHandler handler) {
        register(owner, id, BehaviorValidator.acceptingAnyOptions(), handler);
    }

    void register(Plugin owner, PrismKey id, BehaviorValidator validator, ItemBehaviorHandler handler);

    void unregister(Plugin owner);

    Set<PrismKey> registeredTypes();
}
