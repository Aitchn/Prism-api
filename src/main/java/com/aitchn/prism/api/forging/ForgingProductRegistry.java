package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;
import org.bukkit.plugin.Plugin;

/**
 * Register during addon {@code onLoad}, before content parsing. Duplicate IDs fail. An assembled product that
 * references an unregistered type rejects the candidate. After the owner is disabled, its hooks receive no
 * callbacks and existing stacks of that product become inert until a reload binds a replacement.
 */
public interface ForgingProductRegistry {
    default void register(Plugin owner, ForgingProductType type) {
        register(owner, type, ForgingProductHooks.NONE);
    }

    void register(Plugin owner, ForgingProductType type, ForgingProductHooks hooks);

    void unregister(Plugin owner);

    Map<PrismKey, ForgingProductType> types();
}
