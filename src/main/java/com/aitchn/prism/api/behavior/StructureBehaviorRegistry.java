package com.aitchn.prism.api.behavior;

import com.aitchn.prism.api.PrismKey;
import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface StructureBehaviorRegistry {
    default void register(Plugin owner, PrismKey id, StructureBehaviorHandler handler) {
        register(owner, id, StructureBehaviorValidator.acceptingAnyOptions(), handler);
    }

    void register(
            Plugin owner,
            PrismKey id,
            StructureBehaviorValidator validator,
            StructureBehaviorHandler handler
    );

    void unregister(Plugin owner);

    Set<PrismKey> registeredTypes();
}
