package com.aitchn.prism.api.component;

import com.aitchn.prism.api.PrismKey;
import java.util.Optional;
import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface ComponentRegistry {
    default void register(
            Plugin owner,
            PrismKey id,
            Set<ComponentTarget> targets,
            ComponentFacet facet
    ) {
        register(owner, id, targets, facet, ComponentValidator.acceptingAnyOptions());
    }

    void register(
            Plugin owner,
            PrismKey id,
            Set<ComponentTarget> targets,
            ComponentFacet facet,
            ComponentValidator validator
    );

    void unregister(Plugin owner);

    Set<PrismKey> registeredTypes(ComponentTarget target);

    Optional<ComponentType> type(ComponentTarget target, PrismKey id);
}
