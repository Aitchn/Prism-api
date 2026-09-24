package com.aitchn.prism.api.feedback;

import com.aitchn.prism.api.PrismKey;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/** Shared presentation API. Profiles are declared in content-pack feedback/ YAML files. */
public interface FeedbackService {
    Set<PrismKey> profiles();

    /** Player scheduler required. Only a validated UI profile may be played. */
    void play(Plugin owner, Player player, PrismKey profile);

    /** Emitter region required. No gameplay state is mutated; delivery is bounded and snapshot-bound. */
    void emit(Plugin owner, PrismKey profile, FeedbackOrigin origin);
}
