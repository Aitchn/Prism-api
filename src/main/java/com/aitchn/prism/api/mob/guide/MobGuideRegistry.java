package com.aitchn.prism.api.mob.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Optional;
import org.bukkit.plugin.Plugin;

public interface MobGuideRegistry {
    void register(Plugin owner, PrismKey providerId, MobGuideInformationProvider provider);

    void unregister(Plugin owner);

    long revision();

    Optional<MobGuideEntry> entry(PrismKey mob);

    List<MobGuideEntry> entries();
}
