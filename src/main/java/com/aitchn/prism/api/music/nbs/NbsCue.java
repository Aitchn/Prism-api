package com.aitchn.prism.api.music.nbs;

import com.aitchn.prism.api.music.MusicTransition;

import java.util.List;
import java.util.Map;
import net.kyori.adventure.key.Key;

/**
 * Small author-supplied cue declaration; ranges are [start, end) in NBS ticks.
 * Sections must partition the score from zero, without gaps/overlap; only the final end may pad silence.
 * A null loop inherits the NBS header. An explicit loop overrides it and is reported.
 * NBS does not specify ticks per beat: the creator supplies beatNbsTicks explicitly.
 */
public record NbsCue(List<Section> sections, Map<Integer, String> layerStems,
                     Map<Integer, Key> customSounds, boolean silenceBackgroundMusic,
                     Map<String, MusicTransition> transitions, TimingPolicy timingPolicy) {
    public enum TimingPolicy { EXACT, NEAREST_TICK }

    public NbsCue {
        java.util.Objects.requireNonNull(timingPolicy, "timingPolicy");
        transitions = Map.copyOf(transitions);
        if (transitions.size() > 64) throw invalid("transitions", "At most 64 named transitions");
        for (String name : transitions.keySet()) {
            if (name.length() > 64 || !name.matches("[a-z0-9_]+")) throw invalid("transitions", "Invalid transition name");
        }
        sections = List.copyOf(sections);
        layerStems = Map.copyOf(layerStems);
        customSounds = Map.copyOf(customSounds);
        if (sections.isEmpty() || sections.size() > 64) throw invalid("sections", "Need 1..64 sections");
        for (var entry : layerStems.entrySet()) {
            if (entry.getKey() < 0 || entry.getKey() >= NbsSong.MAX_LAYERS) throw invalid("layerStems", "Invalid layer index");
            if (entry.getValue().length() > 64 || !entry.getValue().matches("[a-z0-9_]+")) throw invalid("layerStems", "Invalid stem id");
        }
        for (var entry : customSounds.entrySet()) {
            if (entry.getKey() < 0 || entry.getKey() > 255 || entry.getValue().asString().length() > 256
                    || !entry.getValue().asString().matches("[a-z0-9._-]+:[a-z0-9._/-]+")) {
                throw invalid("customSounds", "Invalid instrument index or resource key");
            }
            for (String part : entry.getValue().value().split("/", -1)) {
                if (part.isEmpty() || part.equals(".") || part.equals("..")) throw invalid("customSounds", "Unsafe resource key");
            }
        }
    }

    public NbsCue(List<Section> sections, Map<Integer, String> layerStems,
                  Map<Integer, Key> customSounds, boolean silenceBackgroundMusic) {
        this(sections, layerStems, customSounds, silenceBackgroundMusic, Map.of(), TimingPolicy.EXACT);
    }

    /** Exact note timing is the default; quantization must be selected explicitly. */
    public NbsCue(List<Section> sections, Map<Integer, String> layerStems,
                  Map<Integer, Key> customSounds, boolean silenceBackgroundMusic,
                  Map<String, MusicTransition> transitions) {
        this(sections, layerStems, customSounds, silenceBackgroundMusic, transitions, TimingPolicy.EXACT);
    }

    public NbsCue withTimingPolicy(TimingPolicy policy) {
        return new NbsCue(sections, layerStems, customSounds, silenceBackgroundMusic, transitions, policy);
    }

    public record Section(String id, int start, int end, int beatNbsTicks, int beatsPerBar, Boolean loop) {
        public Section {
            if (id == null || id.length() > 64 || !id.matches("[a-z0-9_]+") || start < 0 || end <= start
                    || end > NbsSong.MAX_NBS_TICK + 1 || beatNbsTicks < 1 || beatNbsTicks > 1200
                    || beatsPerBar < 1 || beatsPerBar > 16) throw invalid("section", "Invalid section declaration");
        }
    }

    /** Whole song, inheriting loop semantics and reporting any padding to the next complete bar. */
    public static NbsCue wholeSong(NbsSong song, int beatNbsTicks) {
        if (beatNbsTicks < 1 || beatNbsTicks > 1200) throw invalid("beatNbsTicks", "Invalid beat length");
        int bar = beatNbsTicks * song.header().timeSignature();
        int end = ((song.durationNbsTicks() + bar - 1) / bar) * bar;
        return new NbsCue(List.of(new Section("main", 0, end, beatNbsTicks,
                song.header().timeSignature(), null)), Map.of(), Map.of(), false);
    }

    private static NbsException invalid(String field, String message) {
        return new NbsException(NbsException.Code.INVALID_CUE, -1, field, message);
    }
}
