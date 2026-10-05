package com.aitchn.prism.api.music;

import java.util.Objects;

/**
 * Immutable transport snapshot. Tick is the last advanced section tick, or -1 before playback starts.
 * Immediately after a seek it is the requested tick, which will play on the next active clock tick.
 * Elapsed ticks count active clock advances and never reset; loop counts repeats since the last section change or seek.
 *
 * @since 3.25
 */
public record MusicPosition(String section, int tick, long elapsedTicks, long loop) {
    public MusicPosition {
        Objects.requireNonNull(section, "section");
        if (tick < -1 || elapsedTicks < 0 || loop < 0) throw new IllegalArgumentException("Invalid music position");
    }
}
