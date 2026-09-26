package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;
import org.bukkit.plugin.Plugin;

/**
 * Register during addon {@code onLoad}, before content parsing. Duplicate IDs fail. A part material, product
 * threshold or product trait option referencing an unregistered trait rejects the candidate. After the owner is
 * disabled, its effect receives no callbacks and the trait contributes nothing.
 */
public interface ForgingTraitRegistry {
    default void register(Plugin owner, ForgingTraitType type) {
        register(owner, type, ForgingTraitEffect.NONE);
    }

    void register(Plugin owner, ForgingTraitType type, ForgingTraitEffect effect);

    void unregister(Plugin owner);

    Map<PrismKey, ForgingTraitType> types();
}
