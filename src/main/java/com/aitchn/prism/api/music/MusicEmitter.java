package com.aitchn.prism.api.music;

import java.util.Objects;
import java.util.UUID;

/**
 * A source following each listener, or a fixed position in one world without retaining a Bukkit world object.
 *
 * @since 3.25
 */
public sealed interface MusicEmitter permits MusicEmitter.Following, MusicEmitter.Fixed {
    static MusicEmitter following() { return new Following(); }

    static MusicEmitter fixed(UUID world, double x, double y, double z) { return new Fixed(world, x, y, z); }

    /** Plays at each listener's own position. */
    record Following() implements MusicEmitter { }

    /** Only listeners in {@code world} hear notes emitted at this position. */
    record Fixed(UUID world, double x, double y, double z) implements MusicEmitter {
        public Fixed {
            Objects.requireNonNull(world, "world");
            if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)) {
                throw new IllegalArgumentException("Emitter coordinates must be finite");
            }
        }
    }
}
