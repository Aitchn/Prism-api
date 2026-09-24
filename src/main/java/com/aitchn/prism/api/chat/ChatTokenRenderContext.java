package com.aitchn.prism.api.chat;

import java.util.Locale;
import java.util.Objects;

/**
 * Immutable packet-time context for rendering a captured chat token.
 *
 * <p>Rendering may run on a packet thread. Providers must use only their captured immutable snapshot and this
 * context; they must not read Bukkit player, world, inventory, economy, or other mutable runtime state here.
 */
public record ChatTokenRenderContext(Locale locale) {
    public ChatTokenRenderContext {
        Objects.requireNonNull(locale, "locale");
    }
}
