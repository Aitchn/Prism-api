package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorValidator;
import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface MobControllerRegistry {
    default void register(Plugin owner, PrismKey id, MobController controller) {
        register(owner, id, BehaviorValidator.acceptingAnyOptions(), controller);
    }

    void register(Plugin owner, PrismKey id, BehaviorValidator validator, MobController controller);

    void unregister(Plugin owner);

    Set<PrismKey> registeredTypes();
}
