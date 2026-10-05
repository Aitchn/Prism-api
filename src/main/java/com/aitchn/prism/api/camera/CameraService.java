package com.aitchn.prism.api.camera;

import java.util.Optional;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Takes over what a player sees: from a position the server chooses, looking where the player turns
 * ({@link CameraControl#ANCHORED}), or through a camera the server moves and turns ({@link CameraControl#DIRECTED}).
 * The camera is either an entity that exists only on the viewer's client ({@link CameraRig.Packet}) or a server
 * entity it follows ({@link CameraRig.Following}).
 *
 * <p>A session changes only what the viewer's client shows. The viewer's server-side position is held where it was
 * when the session opened (movement packets they send meanwhile do not move them), and their game mode, inventory,
 * health and passengers are untouched, so nothing needs restoring after a crash. Their client shows only the chunks
 * and entities the server sends for that held position, so an owner who films somewhere else teleports the viewer
 * near it before opening the session. A client does not draw its own player while its camera is elsewhere or its eye
 * is moved, so by default the viewer is shown a stand-in of themselves at the held position ({@link CameraOptions}).
 * Other players see the viewer there as usual.</p>
 *
 * <p>Java clients only; Bedrock clients (Geyser) are not supported yet.</p>
 *
 * @since 3.24
 */
public interface CameraService {
    /** Runtime feature revision, independent of the unreleased 3.25 integration allocation.
     * Revision 1 provides DIRECTED_INPUT, lookPose and full scene transforms. */
    default int interactionRevision() { return 0; }
    /**
     * Opens a session for {@code viewer}; call it on the viewer's owning thread, and for a
     * {@link CameraRig.Following} rig on a thread that owns the followed entity too. A session the same owner holds
     * for the viewer ends with {@link CameraEndReason#REPLACED}. The session ends with {@link CameraSession#end},
     * the viewer leaving (quit, death, respawn, world change), the followed entity's removal, the owner's disable or
     * Prism's shutdown; the viewer's normal view then returns.
     *
     * @throws IllegalStateException    for a disabled owner, an offline viewer, a thread that does not own the viewer
     *                                  or followed entity, or a viewer in another owner's session
     * @throws IllegalArgumentException for a rig in another world than the viewer, or one that follows the viewer
     */
    default CameraSession open(Plugin owner, Player viewer, CameraControl control, CameraRig rig) {
        return open(owner, viewer, control, rig, CameraOptions.DEFAULT);
    }

    /**
     * Opens a session as {@link #open(Plugin, Player, CameraControl, CameraRig)} does, presenting the viewer to
     * themselves as {@code options} say.
     */
    CameraSession open(Plugin owner, Player viewer, CameraControl control, CameraRig rig, CameraOptions options);

    /** The viewer's active session, if any; any thread. */
    Optional<CameraSession> find(Player viewer);
}
