package com.aitchn.prism.api.protection;

import java.util.List;
import org.bukkit.plugin.Plugin;

public interface ProtectionService {
    void register(Plugin owner, ProtectionProvider provider);

    void unregister(Plugin owner);

    List<ProtectionProvider> providers();

    ProtectionDecision query(ProtectionQuery query);
}
