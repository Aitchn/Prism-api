package com.aitchn.prism.api.component;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;
import java.util.Set;

public record ComponentType(
        PrismKey id,
        Set<ComponentTarget> targets,
        ComponentFacet facet
) {
    public ComponentType {
        Objects.requireNonNull(id, "id");
        targets = Set.copyOf(Objects.requireNonNull(targets, "targets"));
        if (targets.isEmpty()) {
            throw new IllegalArgumentException("Component types must support at least one target");
        }
        Objects.requireNonNull(facet, "facet");
        if (targets.contains(ComponentTarget.MACHINE) && facet != ComponentFacet.PRESENTATION) {
            throw new IllegalArgumentException("Machine components currently support presentation data only");
        }
    }
}
