package com.aitchn.prism.api.music;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * A looping part of a track, such as one boss stage: its notes play at their ticks and the section starts again
 * after {@code ticks}. Bar lines every {@code barTicks} are where sections change and where gameplay can align.
 *
 * @param id       {@code [a-z0-9_]+}, unique within the track
 * @param ticks    1..{@value #MAX_TICKS}, a whole number of bars
 * @param barTicks 1..{@value #MAX_BAR_TICKS}
 * @param beatTicks positive beat length that divides the bar length; legacy constructors use one beat per bar
 * @param notes    at most {@value #MAX_NOTES}, each before {@code ticks}; kept sorted by tick
 * @since 3.24
 */
public record MusicSection(String id, int ticks, int barTicks, int beatTicks, List<MusicNote> notes) {
    public static final int MAX_TICKS = 72000;
    public static final int MAX_BAR_TICKS = 1200;
    public static final int MAX_NOTES = 65536;

    /**
     * Creates a section with independently specified beat and bar lengths.
     *
     * @since 3.25
     */
    public MusicSection {
        Objects.requireNonNull(id, "id");
        if (!id.matches("[a-z0-9_]+")) throw new IllegalArgumentException("Section id must match [a-z0-9_]+: " + id);
        if (barTicks < 1 || barTicks > MAX_BAR_TICKS) throw new IllegalArgumentException("A bar lasts 1.." + MAX_BAR_TICKS + " ticks");
        if (beatTicks < 1 || barTicks % beatTicks != 0) throw new IllegalArgumentException("A bar must contain a whole number of positive-length beats");
        if (ticks < 1 || ticks > MAX_TICKS || ticks % barTicks != 0) {
            throw new IllegalArgumentException("A section lasts a whole number of bars, at most " + MAX_TICKS + " ticks");
        }
        Objects.requireNonNull(notes, "notes");
        if (notes.size() > MAX_NOTES) throw new IllegalArgumentException("At most " + MAX_NOTES + " notes per section");
        for (MusicNote note : notes) {
            Objects.requireNonNull(note, "note");
            if (note.tick() >= ticks) throw new IllegalArgumentException("Note at tick " + note.tick() + " is past the section's end");
        }
        notes = notes.stream().sorted(Comparator.comparingInt(MusicNote::tick)).toList();
    }

    /** Legacy sections have one beat per bar. */
    public MusicSection(String id, int ticks, int barTicks, List<MusicNote> notes) {
        this(id, ticks, barTicks, barTicks, notes);
    }
}
