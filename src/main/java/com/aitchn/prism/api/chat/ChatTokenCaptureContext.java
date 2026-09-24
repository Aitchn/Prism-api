package com.aitchn.prism.api.chat;

import java.util.Objects;
import org.bukkit.entity.Player;

/**
 * Player-owned context used to capture mutable sender state for one inline chat token.
 *
 * <p>The callback is invoked on the sender's owning entity scheduler. Providers must copy any mutable state they
 * need and return an immutable snapshot; the sender is never exposed to packet-time rendering.
 */
public record ChatTokenCaptureContext(Player sender) {
    public ChatTokenCaptureContext {
        Objects.requireNonNull(sender, "sender");
    }
}
