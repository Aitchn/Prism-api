package com.aitchn.prism.api.music;

import com.aitchn.prism.api.PrismKey;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * A piece of music made of named sections of notes, played by {@link MusicService#play}. The server plays every note
 * at its tick, so the owner controls the music down to the tick; no audio has to be prepared for the client.
 *
 * @param id                     the owner's name for the track
 * @param sections               1..{@value #MAX_SECTIONS} sections with unique ids
 * @param silenceBackgroundMusic stop the game's own background music for listeners at every bar line while it plays
 * @param stems                  nonempty named stems with unique ids; every note references one of these stems
 * @since 3.24
 */
public record MusicTrack(PrismKey id, List<MusicSection> sections, boolean silenceBackgroundMusic, List<MusicStem> stems) {
    public static final int MAX_SECTIONS = 64;

    /**
     * Creates a track with named stems.
     *
     * @since 3.25
     */
    public MusicTrack {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(sections, "sections");
        Objects.requireNonNull(stems, "stems");
        if (stems.isEmpty()) throw new IllegalArgumentException("A track needs at least one stem");
        Set<String> stemIds = new HashSet<>();
        for (MusicStem stem : stems) {
            Objects.requireNonNull(stem, "stem");
            if (!stemIds.add(stem.id())) throw new IllegalArgumentException("Duplicate stem id: " + stem.id());
        }
        if (sections.isEmpty() || sections.size() > MAX_SECTIONS) {
            throw new IllegalArgumentException("A track needs 1.." + MAX_SECTIONS + " sections");
        }
        Set<String> ids = new HashSet<>();
        for (MusicSection section : sections) {
            Objects.requireNonNull(section, "section");
            if (!ids.add(section.id())) throw new IllegalArgumentException("Duplicate section id: " + section.id());
            for (MusicNote note : section.notes()) {
                if (!stemIds.contains(note.stem())) throw new IllegalArgumentException("Unknown note stem: " + note.stem());
            }
        }
        sections = List.copyOf(sections);
        stems = List.copyOf(stems);
    }

    /** Legacy tracks contain the default {@code main} stem. */
    public MusicTrack(PrismKey id, List<MusicSection> sections, boolean silenceBackgroundMusic) {
        this(id, sections, silenceBackgroundMusic, List.of(new MusicStem(MusicStem.MAIN, "Main")));
    }

    public Optional<MusicSection> section(String id) {
        return sections.stream().filter(section -> section.id().equals(id)).findFirst();
    }

    /**
     * Finds a declared stem by id.
     *
     * @since 3.25
     */
    public Optional<MusicStem> stem(String id) {
        return stems.stream().filter(stem -> stem.id().equals(id)).findFirst();
    }
}
