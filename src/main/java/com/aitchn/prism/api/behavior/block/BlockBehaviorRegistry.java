package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorValidator;
import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface BlockBehaviorRegistry {
    default void register(Plugin owner, PrismKey id, BlockBehaviorHandler handler) {
        register(owner, id, BehaviorValidator.acceptingAnyOptions(), handler);
    }

    void register(Plugin owner, PrismKey id, BehaviorValidator validator, BlockBehaviorHandler handler);

    void unregister(Plugin owner);

    Set<PrismKey> registeredTypes();
}
