package com.aitchn.prism.api.music;

import java.util.Objects;

/**
 * Immutable playback event in increasing sequence order. Previous section and transition are null when inapplicable.
 * Callbacks drain serially on the caller that starts the drain; concurrent controls may enqueue and return before
 * their callback runs. Tick sounds follow that tick's callbacks; slow callbacks stall this playback's clock.
 * There is no guaranteed Folia owner context: schedule player and region work explicitly.
 *
 * @since 3.25
 */
public record MusicEvent(Type type, MusicPosition position, String previousSection,
                         MusicTransition transition, long sequence) {
    public MusicEvent {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(position, "position");
        if (sequence < 1) throw new IllegalArgumentException("Event sequence starts at 1");
    }

    public enum Type {
        STARTED, PAUSED, RESUMED, SEEKED, STOPPED, FINISHED,
        BAR, SECTION, TRANSITION_REQUESTED, TRANSITION_STARTED, TRANSITION_COMPLETED
    }
}
