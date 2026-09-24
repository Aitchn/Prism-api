package com.aitchn.prism.api.chat;

import java.util.Optional;
import net.kyori.adventure.text.Component;

/**
 * Captures sender state once and renders that immutable snapshot for each receiving locale.
 *
 * @param <S> immutable snapshot type owned by the provider
 */
public interface ChatTokenProvider<S> {
    /**
     * Captures one snapshot on the sender's owning entity scheduler.
     *
     * @return a snapshot to share, or empty to leave this token occurrence unchanged
     */
    Optional<S> capture(ChatTokenCaptureContext context);

    /**
     * Renders a previously captured snapshot. This callback may run on a packet thread and must be pure.
     */
    Component render(S snapshot, ChatTokenRenderContext context);
}
