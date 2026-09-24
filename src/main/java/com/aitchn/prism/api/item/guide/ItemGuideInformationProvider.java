package com.aitchn.prism.api.item.guide;

import java.util.Collection;

@FunctionalInterface
public interface ItemGuideInformationProvider {
    Collection<ItemGuideLine> lines(ItemGuideContext context);

    default Collection<ItemGuideChoice> choices(ItemGuideContext context) {
        return java.util.List.of();
    }
}
