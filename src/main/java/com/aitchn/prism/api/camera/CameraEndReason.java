package com.aitchn.prism.api.camera;

/**
 * Why a camera session ended.
 *
 * @since 3.24
 */
public enum CameraEndReason {
    /** {@link CameraSession#end} was called. */
    ENDED,
    /** The same owner opened another session for the viewer. */
    REPLACED,
    /** The viewer quit, died, respawned or changed world. */
    VIEWER_LEFT,
    /** The entity the camera follows was removed. */
    RIG_REMOVED,
    /** The owner was disabled. */
    OWNER_DISABLED,
    /** Prism shut down. */
    SHUTDOWN
}
