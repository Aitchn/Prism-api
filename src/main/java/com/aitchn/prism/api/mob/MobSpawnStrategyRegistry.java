package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorValidator;
import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface MobSpawnStrategyRegistry {
    default void register(Plugin owner, PrismKey id, MobSpawnStrategy strategy) {
        register(owner, id, BehaviorValidator.acceptingAnyOptions(), strategy);
    }

    void register(Plugin owner, PrismKey id, BehaviorValidator validator, MobSpawnStrategy strategy);

    void unregister(Plugin owner);

    Set<PrismKey> registeredTypes();
}
