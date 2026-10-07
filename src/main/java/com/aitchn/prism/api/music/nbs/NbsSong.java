package com.aitchn.prism.api.music.nbs;

import java.util.List;
import java.util.Objects;

/**
 * Accepted v4/v5 field snapshot; not an archive or a claim of editor playback/re-export fidelity. Strings use the editor's byte-to-character encoding (ISO-8859-1).
 * No sample path is opened. Header length is the editor's last tick index, not a game-tick duration.
 */
public record NbsSong(Header header, List<Note> notes, List<Layer> layers,
                      List<Instrument> instruments, boolean layerDataPresent, boolean instrumentDataPresent) {
    public static final int MAX_BYTES = 8 * 1024 * 1024;
    public static final int MAX_NOTES = 65536;
    public static final int MAX_LAYERS = 256;
    public static final int MAX_STRING_BYTES = 16384;
    public static final int MAX_NBS_TICK = 65535;

    public NbsSong {
        Objects.requireNonNull(header, "header");
        notes = List.copyOf(notes);
        layers = List.copyOf(layers);
        instruments = List.copyOf(instruments);
        limit(notes.size() <= MAX_NOTES && layers.size() <= MAX_LAYERS, "events/layers");
        range(layers.size() == header.layerCount(), "layerCount");
        limit(instruments.size() <= (header.version() == 5 ? 240 : 18)
                && header.vanillaInstruments() + instruments.size() <= 256, "instruments");
        range(layerDataPresent || !instrumentDataPresent, "optionalParts");
        int previousTick = -1, previousLayer = -1;
        for (Note note : notes) {
            range(note.layer() < layers.size(), "note.layer");
            range(note.instrument() < header.vanillaInstruments() + instruments.size(), "note.instrument");
            range(note.tick() > previousTick || note.tick() == previousTick && note.layer() > previousLayer,
                    "note.order");
            previousTick = note.tick();
            previousLayer = note.layer();
        }
        if (header.loop()) range(header.loopStart() < durationNbsTicks(header, notes), "loopStart");
    }

    /** Includes the last occupied/header tick; actual notes can extend an inaccurate header. */
    public int durationNbsTicks() { return durationNbsTicks(header, notes); }

    private static int durationNbsTicks(Header header, List<Note> notes) {
        return Math.max(header.lastTick(), notes.isEmpty() ? 0 : notes.getLast().tick()) + 1;
    }

    public record Header(int version, int vanillaInstruments, int lastTick, int layerCount,
                         String name, String author, String originalAuthor, String description,
                         int tempoHundredths, int autoSave, int autoSaveMinutes, int timeSignature,
                         int minutesSpent, int leftClicks, int rightClicks, int blocksAdded, int blocksRemoved,
                         String sourceName, boolean loop, int maxLoops, int loopStart) {
        public Header {
            if (version != 4 && version != 5) throw new NbsException(
                    NbsException.Code.UNSUPPORTED_VERSION, -1, "version", "Only NBS v4/v5 are supported");
            range(vanillaInstruments >= 1 && vanillaInstruments <= 16, "vanillaInstruments");
            range(lastTick >= 0 && lastTick <= MAX_NBS_TICK, "lastTick");
            limit(layerCount >= 0 && layerCount <= MAX_LAYERS, "layerCount");
            range(tempoHundredths > 0 && tempoHundredths <= 65535, "tempo");
            range(autoSave == 0 || autoSave == 1, "autoSave");
            range(autoSaveMinutes >= 1 && autoSaveMinutes <= 60, "autoSaveMinutes");
            range(timeSignature >= 2 && timeSignature <= 8, "timeSignature");
            range(minutesSpent >= 0 && leftClicks >= 0 && rightClicks >= 0
                    && blocksAdded >= 0 && blocksRemoved >= 0, "editingCounters");
            range(maxLoops >= 0 && maxLoops <= 255 && loopStart >= 0 && loopStart <= MAX_NBS_TICK, "loop");
            for (String value : List.of(name, author, originalAuthor, description, sourceName)) string(value);
        }
    }

    public record Note(int tick, int layer, int instrument, int key, int velocity, int pan, int finePitch) {
        public Note {
            range(tick >= 0 && tick <= MAX_NBS_TICK && layer >= 0 && layer < MAX_LAYERS, "note.position");
            range(instrument >= 0 && instrument <= 255 && key >= 0 && key <= 87, "note.instrument/key");
            range(velocity >= 0 && velocity <= 100 && pan >= 0 && pan <= 200, "note.velocity/pan");
            range(finePitch >= Short.MIN_VALUE && finePitch <= Short.MAX_VALUE, "note.finePitch");
        }
    }

    public record Layer(String name, boolean locked, int volume, int pan) {
        public Layer {
            string(name);
            range(volume >= 0 && volume <= 100 && pan >= 0 && pan <= 200, "layer.volume/pan");
        }
    }

    public record Instrument(String name, String samplePath, int key, boolean pressKey) {
        public Instrument {
            string(name);
            string(samplePath);
            range(key >= 0 && key <= 87, "instrument.key");
            // Resource-pack mapping is explicit. Reject absolute/traversing/control-bearing paths even though never opened.
            if (!samplePath.matches("[A-Za-z0-9_. -]+(?:/[A-Za-z0-9_. -]+)*")
                    || java.util.Arrays.stream(samplePath.split("/", -1)).anyMatch(p -> p.equals(".") || p.equals(".."))) {
                throw new NbsException(NbsException.Code.UNSAFE_PATH, -1, "instrument.samplePath", "Unsafe sample path");
            }
        }
    }

    private static void string(String value) {
        Objects.requireNonNull(value, "string");
        limit(value.length() <= MAX_STRING_BYTES, "string");
        range(value.chars().allMatch(c -> c <= 255), "string.encoding");
    }

    private static void range(boolean valid, String field) {
        if (!valid) throw new NbsException(NbsException.Code.INVALID_FIELD, -1, field, "Invalid NBS " + field);
    }

    private static void limit(boolean valid, String field) {
        if (!valid) throw new NbsException(NbsException.Code.LIMIT_EXCEEDED, -1, field, "NBS limit exceeded: " + field);
    }
}
