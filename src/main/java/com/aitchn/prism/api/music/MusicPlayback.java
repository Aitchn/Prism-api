package com.aitchn.prism.api.music;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * One running track. Every method may be called from any thread.
 *
 * @since 3.24
 */
public interface MusicPlayback {
    Plugin owner();

    MusicTrack track();

    /** The section now playing. */
    String section();

    /**
     * Switches to another section at the next bar line, where it starts from its first tick. Asking for the section
     * already playing cancels a pending switch; asking for the section a switch already waits for keeps that
     * switch's bar line.
     *
     * @throws IllegalArgumentException for a section the track does not have
     */
    void section(String id);

    /**
     * Switches to another section at the bar line nearest to {@code ticks} from now (the later one on a tie), and
     * returns how many ticks away it is, or 0 when the section is already playing. Gameplay uses it to time its own
     * change to the same moment: a boss whose stage change takes about {@code ticks} makes it take the returned ticks
     * instead, so the new stage and its music start together without waiting a whole bar.
     *
     * @throws IllegalArgumentException for a section the track does not have
     */
    int sectionNear(String id, int ticks);

    /**
     * Ticks until the next bar line, so gameplay that starts at an arbitrary moment (a split won) can begin its own
     * timeline on the music's next bar.
     */
    int untilBar();

    /**
     * Ends the playback without cutting it: the current bar plays to its end, then {@code outro} (a section of the
     * track) plays once if it is not null, and nothing more starts. Later section requests are ignored.
     *
     * @throws IllegalArgumentException for an outro the track does not have
     */
    void finish(String outro);

    /** Replaces who hears the music; added players hear it from the next tick. */
    void listeners(Collection<? extends Player> players);

    /**
     * Replaces who hears the music, and each listener's variant ({@code null} for none), which decides the notes they
     * hear (see {@link MusicNote#only} and {@link MusicNote#except}). A changed variant applies to future sound
     * delivery, including queued notes. All listeners share the same source clock; entity scheduling can add delay.
     */
    void listeners(Collection<? extends Player> players, Function<? super Player, String> variant);

    Set<UUID> listeners();

    /** Ends scheduling at once. Already-started client sounds continue; use {@code stopSounds} to truncate them. */
    void stop();

    boolean active();

    /**
     * Requests an upcoming boundary change; an immediate change starts on the next active clock tick.
     *
     * @since 3.25
     */
    default void transition(MusicTransition transition) { throw unsupported(); }

    /** @since 3.25 */
    default MusicPosition position() { throw unsupported(); }

    /** @since 3.25 */
    default MusicPlaybackState state() { throw unsupported(); }

    /**
     * Holds the transport and fades; does not truncate already-started client sounds.
     *
     * @since 3.25
     */
    default void pause() { throw unsupported(); }

    /** @since 3.25 */
    default void resume() { throw unsupported(); }

    /**
     * Cancels pending transitions and overlaps; the requested tick plays exactly once on the next active tick.
     *
     * @since 3.25
     */
    default void seek(String section, int tick) { throw unsupported(); }

    /**
     * Master gain in 0..1, multiplied with note, stem, layer, and listener gains for future sounds.
     *
     * @since 3.25
     */
    default float volume() { throw unsupported(); }

    /** @since 3.25 */
    default void volume(float volume) { throw unsupported(); }

    /**
     * Linearly changes the master gain over active clock ticks; zero ticks sets it immediately.
     *
     * @since 3.25
     */
    default void fadeVolume(float volume, int ticks) { throw unsupported(); }

    /** @since 3.25 */
    default float stemVolume(String stem) { throw unsupported(); }

    /** @since 3.25 */
    default void stemVolume(String stem, float volume) { throw unsupported(); }

    /** @since 3.25 */
    default boolean muted(String stem) { throw unsupported(); }

    /** @since 3.25 */
    default void muted(String stem, boolean muted) { throw unsupported(); }

    /** @since 3.25 */
    default boolean solo(String stem) { throw unsupported(); }

    /**
     * If any stem is soloed, only soloed and unmuted stems are audible. Matching ids also apply to added layers.
     *
     * @since 3.25
     */
    default void solo(String stem, boolean solo) { throw unsupported(); }

    /** @since 3.25 */
    default float listenerVolume(UUID listener) { throw unsupported(); }

    /** @since 3.25 */
    default void listenerVolume(UUID listener, float volume) { throw unsupported(); }

    /**
     * Adds a track on this clock. Every base section must match its length, bar length, and beat length.
     *
     * @since 3.25
     */
    default void addLayer(String id, MusicTrack track) { throw unsupported(); }

    /** @since 3.25 */
    default void removeLayer(String id) { throw unsupported(); }

    /**
     * An immutable view of additional layers; the reserved base layer {@code main} is not included.
     *
     * @since 3.25
     */
    default Map<String, MusicTrack> layers() { throw unsupported(); }

    /** @since 3.25 */
    default float layerVolume(String layer) { throw unsupported(); }

    /** @since 3.25 */
    default void layerVolume(String layer, float volume) { throw unsupported(); }

    /** @since 3.25 */
    default MusicEmitter emitter() { throw unsupported(); }

    /** @since 3.25 */
    default void emitter(MusicEmitter emitter) { throw unsupported(); }

    /** @since 3.25 */
    default Sound.Source category() { throw unsupported(); }

    /** @since 3.25 */
    default void category(Sound.Source category) { throw unsupported(); }

    /**
     * Subscribes to future events in sequence order; closing removes the subscription. The first caller drains
     * callbacks outside the playback state lock. Concurrent or reentrant controls append to that drain, so the
     * callback thread can differ from the caller and has no guaranteed region ownership. Schedule player/world
     * work explicitly. A slow callback stalls this playback's clock. Closing or disabling an owner cannot recall
     * a callback whose invocation has already been claimed by the drain.
     *
     * @since 3.25
     */
    default AutoCloseable onEvent(Consumer<MusicEvent> listener) { throw unsupported(); }

    /**
     * Truncates matching client sounds for current listeners, including matching sounds from other producers.
     *
     * @since 3.25
     */
    default void stopSounds(Key sound, Sound.Source category) { throw unsupported(); }

    /**
     * Truncates all client sounds in a category for current listeners; this does not stop scheduling.
     *
     * @since 3.25
     */
    default void stopSounds(Sound.Source category) { throw unsupported(); }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("Playback controls are not supported by this implementation");
    }
}
