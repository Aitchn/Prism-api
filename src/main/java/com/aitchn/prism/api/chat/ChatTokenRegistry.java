package com.aitchn.prism.api.chat;

import com.aitchn.prism.api.PrismKey;
import java.util.Set;
import org.bukkit.plugin.Plugin;

/** Registration surface for inline chat tokens such as {@code $item}. */
public interface ChatTokenRegistry {
    /**
     * Registers a token without a permission requirement.
     *
     * <p>The chat alias excludes the leading {@code $} and must match {@code [a-z0-9_-]+}.
     */
    <S> void register(Plugin owner, PrismKey id, String token, ChatTokenProvider<S> provider);

    /**
     * Registers a token guarded by a sender permission.
     *
     * <p>The chat alias excludes the leading {@code $} and must match {@code [a-z0-9_-]+}. Duplicate ids and
     * duplicate aliases are rejected rather than replaced.
     */
    <S> void register(Plugin owner, PrismKey id, String token, String permission, ChatTokenProvider<S> provider);

    void unregister(Plugin owner);

    Set<PrismKey> registeredTokens();
}
