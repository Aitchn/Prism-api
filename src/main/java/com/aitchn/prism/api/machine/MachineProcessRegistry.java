package com.aitchn.prism.api.machine;

import com.aitchn.prism.api.PrismKey;
import java.util.Set;
import org.bukkit.plugin.Plugin;

public interface MachineProcessRegistry {
    void register(Plugin owner, PrismKey id, MachineProcessValidator validator);

    void register(Plugin owner, PrismKey id, MachineProcessValidator validator, MachineProcessPreparer preparer);

    void unregister(Plugin owner);

    Set<PrismKey> registeredTypes();
}
