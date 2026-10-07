package com.aitchn.prism.api.music.nbs;

import com.aitchn.prism.api.music.MusicTransition;
import com.aitchn.prism.api.music.MusicTrack;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/** Immutable imported score and measured conversion facts. Use MusicService.playNbs to honor one-shot sections. */
public record NbsImport(NbsSong song, MusicTrack track, Set<String> oneShotSections, Report report,
                        Map<String, MusicTransition> transitions) {
    public NbsImport {
        Objects.requireNonNull(song, "song");
        Objects.requireNonNull(track, "track");
        Objects.requireNonNull(report, "report");
        transitions = Map.copyOf(transitions);
        for (var transition : transitions.values()) {
            if (track.section(transition.section()).isEmpty()) throw new IllegalArgumentException("Unknown transition target");
        }
        oneShotSections = Set.copyOf(oneShotSections);
        for (String id : oneShotSections) {
            if (track.section(id).isEmpty()) throw new IllegalArgumentException("Unknown one-shot section: " + id);
        }
    }

    public NbsImport(NbsSong song, MusicTrack track, Set<String> oneShotSections, Report report) {
        this(song, track, oneShotSections, report, Map.of());
    }

    public enum Diagnostic {
        BYTE_ENCODED_TEXT, DEFAULTED_LAYERS, OMITTED_INSTRUMENT_TABLE, HEADER_LENGTH_MISMATCH,
        LOOP_OVERRIDDEN, PADDED_FINAL_BAR, SILENT_NOTES, QUANTIZED_NOTES, COLLIDING_NOTES, LOCKED_LAYER_PLAYBACK
    }

    /** One changed note, including silent notes; gameTick is section-relative and error is signed. */
    public record TimingChange(int noteIndex, int sourceTick, int sourceLayer, String section,
                               int gameTick, double errorMillis) {
        public TimingChange {
            if (noteIndex < 0 || noteIndex >= NbsSong.MAX_NOTES || sourceTick < 0 || sourceTick > NbsSong.MAX_NBS_TICK
                    || sourceLayer < 0 || sourceLayer >= NbsSong.MAX_LAYERS || gameTick < 0
                    || !Double.isFinite(errorMillis) || errorMillis == 0 || Math.abs(errorMillis) > 25.000001) {
                throw new IllegalArgumentException("Invalid NBS timing change");
            }
            Objects.requireNonNull(section, "section");
        }
    }

    /** Audible notes sharing a section tick; indices refer to song.notes(), and none are merged. */
    public record Collision(String section, int gameTick, List<Integer> noteIndices) {
        public Collision {
            Objects.requireNonNull(section, "section");
            noteIndices = List.copyOf(noteIndices);
            if (gameTick < 0 || noteIndices.size() < 2 || noteIndices.size() > NbsSong.MAX_NOTES
                    || noteIndices.stream().anyMatch(i -> i < 0 || i >= NbsSong.MAX_NOTES)) {
                throw new IllegalArgumentException("Invalid NBS collision");
            }
            int previous = -1;
            for (int index : noteIndices) {
                if (index <= previous) throw new IllegalArgumentException("Collision indices must increase");
                previous = index;
            }
        }
    }

    /** Mean absolute error includes all source notes, including silent/exact notes; empty scores report zero. */
    public record Report(int sourceNotes, int audibleNotes, int silentNotes, int quantizedNotes,
                         int collisionNotes, double maximumErrorMillis, double meanErrorMillis,
                         int paddedNbsTicks, List<Diagnostic> diagnostics, NbsCue.TimingPolicy timingPolicy,
                         List<TimingChange> timingChanges, List<Collision> collisions) {
        public Report {
            diagnostics = List.copyOf(diagnostics);
            timingChanges = List.copyOf(timingChanges);
            collisions = List.copyOf(collisions);
            Objects.requireNonNull(timingPolicy, "timingPolicy");
            if (sourceNotes < 0 || sourceNotes > NbsSong.MAX_NOTES || audibleNotes < 0 || silentNotes < 0
                    || sourceNotes != audibleNotes + silentNotes || quantizedNotes != timingChanges.size()
                    || quantizedNotes > sourceNotes || collisionNotes < 0 || collisionNotes > audibleNotes
                    || !Double.isFinite(maximumErrorMillis) || maximumErrorMillis < 0 || maximumErrorMillis > 25.000001
                    || !Double.isFinite(meanErrorMillis) || meanErrorMillis < 0 || meanErrorMillis > maximumErrorMillis
                    || paddedNbsTicks < 0 || collisions.size() > audibleNotes
                    || collisionNotes != collisions.stream().mapToLong(c -> c.noteIndices().size() - 1).sum()
                    || timingChanges.stream().anyMatch(c -> c.noteIndex() >= sourceNotes)
                    || collisions.stream().flatMap(c -> c.noteIndices().stream()).anyMatch(i -> i >= sourceNotes)
                    || timingPolicy == NbsCue.TimingPolicy.EXACT && quantizedNotes != 0) {
                throw new IllegalArgumentException("Invalid NBS conversion report");
            }
        }
    }
}
