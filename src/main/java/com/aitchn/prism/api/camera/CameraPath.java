package com.aitchn.prism.api.camera;

import java.util.List;
import java.util.Objects;

/**
 * A camera movement through keyframes, played by {@link CameraSession#play}. Between two keyframes the camera moves
 * over the ticks that separate them, spread by the later keyframe's easing.
 *
 * @param keyframes 2..{@value #MAX_KEYFRAMES}, the first at tick 0, ticks strictly increasing, the last at most
 *                  {@value #MAX_TICKS}
 * @param smooth    true for a curve through the keyframes (Catmull-Rom), whose speed and direction change without
 *                  corners; false for straight lines between them
 * @since 3.24
 */
public record CameraPath(List<CameraKeyframe> keyframes, boolean smooth) {
    public static final int MAX_KEYFRAMES = 1024;
    public static final int MAX_TICKS = 72000;

    public CameraPath {
        Objects.requireNonNull(keyframes, "keyframes");
        if (keyframes.size() < 2 || keyframes.size() > MAX_KEYFRAMES) {
            throw new IllegalArgumentException("A camera path needs 2.." + MAX_KEYFRAMES + " keyframes");
        }
        int previous = -1;
        for (CameraKeyframe keyframe : keyframes) {
            Objects.requireNonNull(keyframe, "keyframe");
            if (previous < 0 && keyframe.tick() != 0) throw new IllegalArgumentException("A camera path starts at tick 0");
            if (keyframe.tick() <= previous) throw new IllegalArgumentException("Keyframe ticks must increase");
            previous = keyframe.tick();
        }
        if (previous > MAX_TICKS) throw new IllegalArgumentException("A camera path lasts at most " + MAX_TICKS + " ticks");
        keyframes = List.copyOf(keyframes);
    }

    /** The tick of the last keyframe. */
    public int duration() {
        return keyframes.getLast().tick();
    }

    /** The pose at {@code tick} (clamped to the path), including fractions of a tick. */
    public CameraPose pose(double tick) {
        if (!Double.isFinite(tick)) throw new IllegalArgumentException("tick must be finite");
        double t = Math.clamp(tick, 0, duration());
        int segment = 0;
        while (segment < keyframes.size() - 2 && keyframes.get(segment + 1).tick() <= t) segment++;
        CameraKeyframe from = keyframes.get(segment);
        CameraKeyframe to = keyframes.get(segment + 1);
        double u = to.easing().apply((t - from.tick()) / (to.tick() - from.tick()));
        if (!smooth) return CameraPose.interpolate(from.pose(), to.pose(), u);
        CameraPose p1 = from.pose();
        CameraPose p2 = to.pose();
        CameraPose p0 = segment > 0 ? keyframes.get(segment - 1).pose() : p1;
        CameraPose p3 = segment + 2 < keyframes.size() ? keyframes.get(segment + 2).pose() : p2;
        // Unwrap the yaws around p1 so the curve turns the short way between each pair of neighbours.
        double y1 = p1.yaw();
        double y0 = y1 - CameraPose.turn(p0.yaw(), p1.yaw());
        double y2 = y1 + CameraPose.turn(p1.yaw(), p2.yaw());
        double y3 = y2 + CameraPose.turn(p2.yaw(), p3.yaw());
        double pitch = Math.clamp(spline(p0.pitch(), p1.pitch(), p2.pitch(), p3.pitch(), u), -90, 90);
        return new CameraPose(
                spline(p0.x(), p1.x(), p2.x(), p3.x(), u),
                spline(p0.y(), p1.y(), p2.y(), p3.y(), u),
                spline(p0.z(), p1.z(), p2.z(), p3.z(), u),
                (float) spline(y0, y1, y2, y3, u),
                (float) pitch);
    }

    /** Uniform Catmull-Rom between {@code b} and {@code c}. */
    private static double spline(double a, double b, double c, double d, double u) {
        double u2 = u * u, u3 = u2 * u;
        return 0.5 * (2 * b + (c - a) * u + (2 * a - 5 * b + 4 * c - d) * u2 + (3 * b - a - 3 * c + d) * u3);
    }
}
