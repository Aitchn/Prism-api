package com.aitchn.prism.api.camera;

import java.util.Objects;

/**
 * A pose a {@link CameraPath} reaches at a tick.
 *
 * @param tick   ticks from the path's start, 0 or later
 * @param easing how the movement from the previous keyframe arrives at this one; ignored on the first keyframe
 * @since 3.24
 */
public record CameraKeyframe(int tick, CameraPose pose, CameraEasing easing) {
    public CameraKeyframe {
        if (tick < 0) throw new IllegalArgumentException("A keyframe's tick is 0 or later");
        Objects.requireNonNull(pose, "pose");
        Objects.requireNonNull(easing, "easing");
    }

    public CameraKeyframe(int tick, CameraPose pose) {
        this(tick, pose, CameraEasing.LINEAR);
    }
}
