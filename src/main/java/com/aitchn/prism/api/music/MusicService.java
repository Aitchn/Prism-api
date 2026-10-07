package com.aitchn.prism.api.music;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.music.nbs.NbsCue;
import com.aitchn.prism.api.music.nbs.NbsImport;
import java.util.concurrent.CompletionStage;

import org.bukkit.plugin.Plugin;

/**
 * Server-controlled music for groups of players, such as a boss fight's participants. The server plays the music note
 * by note: every tick, each listener receives one sound for every note of the current section due on that tick. The
 * owner therefore controls what plays and when down to the tick, and gameplay can align to any tick of the music;
 * no audio is prepared for the client. Timing follows the server's ticks, so a slow server slows the music.
 *
 * <p>By default notes follow the listener in the "Jukebox/Note Blocks" ({@code record}) sound category. Playback
 * controls can select a fixed world position and another category. The client's category volume still applies.
 * What Bedrock clients (Geyser) hear is unverified.</p>
 *
 * @since 3.24
 */
public interface MusicService {
    /**
     * Whether every playback returned by this service supports the music-controls revision: stems, transport,
     * mixing, layers, transitions, events and emitters. The API minor alone cannot identify this revision.
     * Older service implementations retain their legacy behavior and return {@code false}.
     *
     * @since 3.25
     */
    default boolean supportsPlaybackControls() {
        return false;
    }

    /**
     * Starts a track at the first tick of {@code section}, with no listeners yet; any thread. The playback ends with
     * {@link MusicPlayback#stop}, {@link MusicPlayback#finish}, its owner's disable, or Prism's shutdown.
     *
     * @throws IllegalArgumentException for a section the track does not have
     * @throws IllegalStateException    for a disabled owner
     */
    MusicPlayback play(Plugin owner, MusicTrack track, String section);

    /** Availability gate for the NBS-enabled API 3.25 build; older implementations return false. */
    default boolean supportsNbsImport() { return false; }

    /**
     * Parses and compiles bounded v4/v5 bytes off Folia region/player threads. The byte array is copied before return.
     * Null cue selects the whole song with four NBS ticks per beat and exact note timing. Other beat grids
     * or explicitly chosen nearest-tick quantization require a cue; fractional times otherwise reject.
     * Failed imports never start playback. Completion has no Folia owner context; schedule gameplay explicitly.
     * NbsException is the stable content failure (inside CompletionException for asynchronous failures).
     */
    default CompletionStage<NbsImport> importNbs(
            PrismKey id, byte[] bytes, NbsCue cue) {
        throw new UnsupportedOperationException("NBS import is not supported by this implementation");
    }

    /** Same import pipeline with strict schema-1 cue YAML (at most 65536 characters); parsed asynchronously. */
    default CompletionStage<NbsImport> importNbsYaml(
            PrismKey id, byte[] bytes, String cueYaml) {
        throw new UnsupportedOperationException("NBS cue YAML is not supported by this implementation");
    }

    /** Uses the existing playback scheduler/controls, honoring imported one-shot section policy. Any thread. */
    default MusicPlayback playNbs(Plugin owner, NbsImport imported, String section) {
        throw new UnsupportedOperationException("NBS playback is not supported by this implementation");
    }
}
