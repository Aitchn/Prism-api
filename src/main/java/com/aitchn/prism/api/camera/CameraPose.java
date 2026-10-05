package com.aitchn.prism.api.camera;

import java.util.Objects;
import org.bukkit.Location;

/**
 * A camera position and direction in a world. For {@link CameraControl#ANCHORED} sessions the position is the viewer's
 * eye; for {@link CameraControl#DIRECTED} sessions it is the camera itself.
 *
 * @param yaw   degrees, as Minecraft measures it (0 = south, 90 = west); any finite value, kept in [-180, 180)
 * @param pitch degrees, -90 (up) to 90 (down)
 * @since 3.24
 */
public record CameraPose(double x, double y, double z, float yaw, float pitch) {
    public CameraPose {
        if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)) {
            throw new IllegalArgumentException("A camera position must be finite");
        }
        if (!Float.isFinite(yaw)) throw new IllegalArgumentException("A camera yaw must be finite");
        if (!(pitch >= -90F && pitch <= 90F)) throw new IllegalArgumentException("A camera pitch must be -90..90");
        yaw = wrap(yaw);
    }

    /** The position and direction of {@code location}; its world is not part of the pose. */
    public static CameraPose of(Location location) {
        Objects.requireNonNull(location, "location");
        return new CameraPose(location.getX(), location.getY(), location.getZ(), location.getYaw(),
                Math.clamp(location.getPitch(), -90F, 90F));
    }

    /** The same pose looking at {@code yaw} and {@code pitch}. */
    public CameraPose facing(float yaw, float pitch) {
        return new CameraPose(x, y, z, yaw, pitch);
    }

    /** The same direction from another position. */
    public CameraPose at(double x, double y, double z) {
        return new CameraPose(x, y, z, yaw, pitch);
    }

    /** The pose at {@code x, y, z} looking at the point {@code tx, ty, tz}. */
    public static CameraPose lookingAt(double x, double y, double z, double tx, double ty, double tz) {
        double dx = tx - x, dy = ty - y, dz = tz - z;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 1e-9 && Math.abs(dy) < 1e-9) throw new IllegalArgumentException("The target is the camera position");
        float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        float pitch = (float) Math.toDegrees(-Math.atan2(dy, horizontal));
        return new CameraPose(x, y, z, yaw, pitch);
    }

    /**
     * The pose {@code t} of the way from {@code from} to {@code to} (0..1): a straight line, with the yaw turning the
     * short way round.
     */
    public static CameraPose interpolate(CameraPose from, CameraPose to, double t) {
        return new CameraPose(
                from.x + (to.x - from.x) * t,
                from.y + (to.y - from.y) * t,
                from.z + (to.z - from.z) * t,
                (float) (from.yaw + turn(from.yaw, to.yaw) * t),
                (float) (from.pitch + (to.pitch - from.pitch) * t));
    }

    /** The signed turn in degrees, -180..180, that takes {@code from} to {@code to} the short way. */
    static double turn(double from, double to) {
        double delta = (to - from) % 360;
        if (delta >= 180) delta -= 360;
        if (delta < -180) delta += 360;
        return delta;
    }

    static float wrap(double degrees) {
        double wrapped = degrees % 360;
        if (wrapped >= 180) wrapped -= 360;
        if (wrapped < -180) wrapped += 360;
        return (float) wrapped;
    }
}
