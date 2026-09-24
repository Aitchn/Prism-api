package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorValidator;
import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface MobSpawnConditionRegistry {
    default void register(Plugin owner, PrismKey id, MobSpawnCondition condition) {
        register(owner, id, BehaviorValidator.acceptingAnyOptions(), condition);
    }

    void register(Plugin owner, PrismKey id, BehaviorValidator validator, MobSpawnCondition condition);

    void unregister(Plugin owner);

    Set<PrismKey> registeredTypes();
}
