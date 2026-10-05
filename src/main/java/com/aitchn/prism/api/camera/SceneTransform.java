package com.aitchn.prism.api.camera;

import java.util.Objects;

/** Whole-group pivot, positive uniform scale and Y-X-Z Euler rotation in radians, then translation.
 * Forward: origin + scale * rotation * (point - pivot). @since 3.25 */
public record SceneTransform(ScenePoint origin, ScenePoint pivot, double scale, double yaw, double pitch, double roll) {
    /** Retains the original yaw-only constructor. */
    public SceneTransform(ScenePoint origin, ScenePoint pivot, double scale, double yaw) {
        this(origin, pivot, scale, yaw, 0, 0);
    }
    public SceneTransform {
        Objects.requireNonNull(origin); Objects.requireNonNull(pivot);
        if (!Double.isFinite(scale) || scale <= 0 || !Double.isFinite(yaw)
                || !Double.isFinite(pitch) || !Double.isFinite(roll))
            throw new IllegalArgumentException("Finite rotation and positive finite scale required");
    }
    public ScenePoint apply(ScenePoint p) {
        double x = p.x() - pivot.x(), y = p.y() - pivot.y(), z = p.z() - pivot.z();
        double cr = Math.cos(roll), sr = Math.sin(roll), cp = Math.cos(pitch), sp = Math.sin(pitch);
        double rx = cr*x-sr*y, ry = sr*x+cr*y;
        double py = cp*ry-sp*z, pz = sp*ry+cp*z;
        double c = Math.cos(yaw), s = Math.sin(yaw);
        return new ScenePoint(origin.x() + scale*(c*rx+s*pz), origin.y() + scale*py,
                origin.z() + scale*(-s*rx+c*pz));
    }
    public ScenePoint inverse(ScenePoint p) {
        var d = inverseDirection(new ScenePoint(p.x()-origin.x(), p.y()-origin.y(), p.z()-origin.z()));
        return new ScenePoint(pivot.x()+d.x(), pivot.y()+d.y(), pivot.z()+d.z());
    }
    /** Inverse linear transform, without translation; preserves ray precision far from zero. */
    public ScenePoint inverseDirection(ScenePoint direction) {
        double x=direction.x()/scale, y=direction.y()/scale, z=direction.z()/scale;
        double c = Math.cos(yaw), s = Math.sin(yaw);
        double rx=c*x-s*z, rz=s*x+c*z;
        double cp=Math.cos(pitch), sp=Math.sin(pitch), cr=Math.cos(roll), sr=Math.sin(roll);
        double py=cp*y+sp*rz, pz=-sp*y+cp*rz;
        return new ScenePoint(cr*rx+sr*py, -sr*rx+cr*py, pz);
    }
}
