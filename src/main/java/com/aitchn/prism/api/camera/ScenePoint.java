package com.aitchn.prism.api.camera;

/** Immutable finite position/vector, independent of Bukkit world ownership. @since 3.25 */
public record ScenePoint(double x, double y, double z) {
    public ScenePoint {
        if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z))
            throw new IllegalArgumentException("Coordinates must be finite");
    }
}
