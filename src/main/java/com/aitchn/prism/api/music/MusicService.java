package com.aitchn.prism.api.music;

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
}
