package com.aitchn.prism.api.music;

import java.util.Objects;

/**
 * A section change, optionally overlapping the outgoing and incoming note timelines for {@code fadeTicks}.
 * Gains affect newly emitted notes only; sounds already started on a client cannot be faded independently.
 *
 * @since 3.25
 */
public record MusicTransition(String section, Timing timing, int fadeTicks) {
    public MusicTransition {
        Objects.requireNonNull(section, "section");
        Objects.requireNonNull(timing, "timing");
        if (!section.matches("[a-z0-9_]+")) throw new IllegalArgumentException("Section id must match [a-z0-9_]+: " + section);
        if (fadeTicks < 0) throw new IllegalArgumentException("Fade ticks must be 0 or greater");
    }

    public MusicTransition(String section, Timing timing) {
        this(section, timing, 0);
    }

    /** Boundaries are strictly upcoming; IMMEDIATE means the next active clock tick. */
    public enum Timing {
        IMMEDIATE, NEXT_BEAT, NEXT_BAR, SECTION_END
    }
}
