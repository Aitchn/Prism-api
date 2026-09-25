package com.aitchn.prism.api.status;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Experimental generic online-player statuses; no types are registered by Prism.
 * Death, logout, source/type owner unregister and Prism shutdown discard instances.
 * Content reload preserves these independent live registrations. There is no disk persistence.
 */
public interface StatusService {
    /** May be called during addon onLoad. Duplicate ids fail, never replace existing registrations. */
    void register(Plugin owner, StatusType type, StatusBehavior behavior);

    /** Invalidates owned types and applications immediately; no cleanup callback enters a disabled addon. */
    void unregister(Plugin owner);

    /** Immutable registered definitions, including registrations made before their owners enable. */
    Map<PrismKey, StatusType> types();

    /**
     * Requires the player's entity owner and enabled source/type owners.
     * Same owner/type/source refreshes to max(current, requested) level and duration.
     * Different sources are independent instances with independent callbacks; at most 32 per player.
     */
    StatusSnapshot apply(Plugin sourceOwner, Player player, StatusApplication application);

    /** Requires the player's owner. Only the application owner can remove the specified instance. */
    boolean remove(Plugin sourceOwner, Player player, UUID instanceId);

    /** Thread-safe immutable observation of live instances, ordered by instance UUID. */
    List<StatusSnapshot> snapshot(UUID playerId);
}
