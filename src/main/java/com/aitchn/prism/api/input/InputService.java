package com.aitchn.prism.api.input;

import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/** Java 26.2 semantic observation. No client injection or raw keyboard/mouse capture. @since 3.25 */
public interface InputService {
    /** Call on the player's owner thread, with an enabled owner. Callbacks also run there. */
    InputSubscription subscribe(Plugin owner, Player player, InputOptions options, InputListener listener);
    /** Call on the player's owner thread. v1 explicitly reports nativeCancellation=false for every source. */
    Map<InputSource, InputCapability> capabilities(Player player);
    /** Close this owner's subscriptions without invoking the disabled owner's callbacks. Any thread. */
    void unsubscribe(Plugin owner);
}
