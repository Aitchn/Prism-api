package com.aitchn.prism.api.camera;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.plugin.Plugin;

/**
 * One viewer's camera. Every method may be called from any thread; movements take effect on the viewer's next tick.
 *
 * <p>Movements apply to {@link CameraRig.Packet} rigs, whose camera the owner moves; a {@link CameraRig.Following}
 * camera goes where its entity goes, so they throw {@link IllegalStateException} there. For an
 * {@link CameraControl#ANCHORED} session a pose's position is the viewer's eye and its direction is ignored, as the
 * viewer turns their own head; use {@link #face} to turn it for them.</p>
 *
 * @since 3.24
 */
public interface CameraSession {
    Plugin owner();

    UUID viewer();

    CameraControl control();

    CameraRig rig();

    CameraOptions options();

    boolean active();

    /**
     * Where the camera is now, for a packet rig.
     *
     * @throws IllegalStateException for a following rig
     */
    CameraPose pose();

    /** Last owner-tick view: packet camera origin plus the actual anchored viewer look, or directed
     * camera direction. Empty before the first sample, after end, or for following rigs (whose
     * remote entity is not owned here). This is not a client interpolation acknowledgement. @since 3.25 */
    default java.util.Optional<CameraPose> viewPose() {
        return java.util.Optional.empty();
    }

    /** Last owner-tick camera origin with the viewer's independent look direction. For
     * DIRECTED_INPUT this is input, not the rendered view. Empty before sampling and after end. */
    default java.util.Optional<CameraPose> lookPose() {
        return java.util.Optional.empty();
    }

    /** Cuts to {@code pose}, stopping any movement in progress. */
    void move(CameraPose pose);

    /**
     * Moves to {@code pose} in a straight line over {@code ticks} (0 cuts), turning the short way, stopping any
     * movement in progress.
     *
     * @throws IllegalArgumentException for ticks outside 0..{@value CameraPath#MAX_TICKS}
     */
    void move(CameraPose pose, int ticks, CameraEasing easing);

    /**
     * Plays {@code path} from its first keyframe (a cut to it), stopping any movement in progress. The camera stays
     * at the last keyframe afterwards. The result completes with true when the path finishes and false when another
     * movement or the session's end interrupts it.
     */
    CompletableFuture<Boolean> play(CameraPath path);

    /**
     * Turns the viewer's own head to {@code yaw} and {@code pitch}, for {@link CameraControl#ANCHORED} sessions; the
     * viewer can turn away again. An anchored session on a packet rig starts facing the rig's start pose.
     *
     * DIRECTED_INPUT also permits this operation, without moving its rendered camera.
     * @throws IllegalStateException for a DIRECTED session
     */
    void face(float yaw, float pitch);

    /** Ends the session and gives the viewer their own view back. Does nothing once ended. */
    void end();

    /** Completes, once, with the reason the session ended. */
    CompletableFuture<CameraEndReason> ended();
}
