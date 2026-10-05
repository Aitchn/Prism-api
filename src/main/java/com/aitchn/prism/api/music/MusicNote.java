package com.aitchn.prism.api.music;

import java.util.Objects;
import net.kyori.adventure.key.Key;

/**
 * One note of a music section: a sound event played on every listener at a tick of the section, such as a note
 * block ({@code minecraft:block.note_block.harp}) or any other game or resource-pack sound.
 *
 * <p>A note may be limited to listeners of one variant ({@code only}) or kept from them ({@code except}), so listeners
 * with different variants (such as players in two realms) hear different notes in time with each other.</p>
 *
 * @param tick   the tick within the section, from 0
 * @param sound  sound event id
 * @param volume 0 (exclusive) to 1
 * @param pitch  0.5 to 2, as the client clamps it (a note block's 24 steps)
 * @param only   variant id ({@code [a-z0-9_]+}) of the only listeners who hear it, or null
 * @param except variant id of the listeners who do not hear it, or null; at most one of the two is set
 * @param stem   declared stem id within the track; legacy constructors use {@code main}
 * @since 3.24
 */
public record MusicNote(int tick, Key sound, float volume, float pitch, String only, String except, String stem) {
    /**
     * Creates a note assigned to a declared stem.
     *
     * @since 3.25
     */
    public MusicNote {
        if (tick < 0) throw new IllegalArgumentException("A note's tick is 0 or later");
        Objects.requireNonNull(sound, "sound");
        Objects.requireNonNull(stem, "stem");
        if (!stem.matches("[a-z0-9_]+")) throw new IllegalArgumentException("Stem id must match [a-z0-9_]+: " + stem);
        if (!(volume > 0 && volume <= 1)) throw new IllegalArgumentException("Note volume must be in (0, 1]");
        if (!(pitch >= 0.5F && pitch <= 2F)) throw new IllegalArgumentException("Note pitch must be 0.5..2");
        if (only != null && except != null) throw new IllegalArgumentException("A note is either only for or except a variant");
        for (String variant : new String[] {only, except}) {
            if (variant != null && !variant.matches("[a-z0-9_]+")) throw new IllegalArgumentException("Variant id must match [a-z0-9_]+: " + variant);
        }
    }

    public MusicNote(int tick, Key sound, float volume, float pitch) {
        this(tick, sound, volume, pitch, null, null);
    }

    /** Legacy notes belong to the default {@code main} stem. */
    public MusicNote(int tick, Key sound, float volume, float pitch, String only, String except) {
        this(tick, sound, volume, pitch, only, except, MusicStem.MAIN);
    }

    /** Whether a listener with {@code variant} (null for none) hears this note. */
    public boolean heardBy(String variant) {
        if (only != null) return only.equals(variant);
        return except == null || !except.equals(variant);
    }

    /** A note block's pitch for one of its 25 steps (0 = F#, 12 = pitch 1, 24 = pitch 2). */
    public static float noteBlockPitch(int step) {
        if (step < 0 || step > 24) throw new IllegalArgumentException("A note block has steps 0..24");
        return (float) Math.pow(2, (step - 12) / 12.0);
    }
}
