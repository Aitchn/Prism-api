package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorValidator;
import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface MobBehaviorRegistry {
    default void register(Plugin owner, PrismKey id, MobBehaviorHandler handler) {
        register(owner, id, BehaviorValidator.acceptingAnyOptions(), handler);
    }

    void register(Plugin owner, PrismKey id, BehaviorValidator validator, MobBehaviorHandler handler);

    void unregister(Plugin owner);

    Set<PrismKey> registeredTypes();
}
