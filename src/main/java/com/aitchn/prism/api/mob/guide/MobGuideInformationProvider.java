package com.aitchn.prism.api.mob.guide;

import java.util.Collection;

@FunctionalInterface
public interface MobGuideInformationProvider {
    Collection<MobGuideSection> sections(MobGuideContext context);
}
