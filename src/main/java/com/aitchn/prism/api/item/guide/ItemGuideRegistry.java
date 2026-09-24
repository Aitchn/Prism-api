package com.aitchn.prism.api.item.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Optional;
import org.bukkit.plugin.Plugin;

public interface ItemGuideRegistry {
    void register(Plugin owner, PrismKey providerId, ItemGuideInformationProvider provider);

    void unregister(Plugin owner);

    long revision();

    Optional<ItemGuideEntry> entry(PrismKey item);

    List<ItemGuideEntry> entries();
}
