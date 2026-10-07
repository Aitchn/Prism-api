package com.aitchn.prism.api.hit;

import java.util.Objects;
import java.util.Optional;

/** Axis-aligned box. Sweeping a box uses Minkowski expansion, not an exact sphere approximation. */
public record HitBox(HitVector minimum, HitVector maximum) {
    public HitBox {
        Objects.requireNonNull(minimum, "minimum");
        Objects.requireNonNull(maximum, "maximum");
        if (minimum.x() > maximum.x() || minimum.y() > maximum.y() || minimum.z() > maximum.z()) {
            throw new IllegalArgumentException("Inverted box");
        }
    }
    public HitBox translate(HitVector offset) { return new HitBox(minimum.add(offset), maximum.add(offset)); }
    public HitBox expand(HitVector halfSize) {
        if (halfSize.x() < 0 || halfSize.y() < 0 || halfSize.z() < 0) throw new IllegalArgumentException("Negative size");
        return new HitBox(minimum.subtract(halfSize), maximum.add(halfSize));
    }
    public boolean contains(HitVector p) {
        return p.x() >= minimum.x() && p.x() <= maximum.x() && p.y() >= minimum.y() && p.y() <= maximum.y()
                && p.z() >= minimum.z() && p.z() <= maximum.z();
    }
    public static HitBox swept(HitVector from, HitVector to, HitVector halfSize) {
        return new HitBox(new HitVector(Math.min(from.x(), to.x()), Math.min(from.y(), to.y()), Math.min(from.z(), to.z())),
                new HitVector(Math.max(from.x(), to.x()), Math.max(from.y(), to.y()), Math.max(from.z(), to.z())))
                .expand(halfSize);
    }
    public Optional<HitIntersection> intersect(HitVector from, HitVector to) {
        HitVector path = to.subtract(from);
        double enter = 0, exit = 1;
        HitVector normal = HitVector.ZERO;
        double[] origin = {from.x(), from.y(), from.z()}, direction = {path.x(), path.y(), path.z()};
        double[] low = {minimum.x(), minimum.y(), minimum.z()}, high = {maximum.x(), maximum.y(), maximum.z()};
        for (int axis = 0; axis < 3; axis++) {
            // Exact zero preserves arbitrarily slow motion and contact at t=0.
            if (direction[axis] == 0) {
                if (origin[axis] < low[axis] || origin[axis] > high[axis]) return Optional.empty();
                continue;
            }
            double first = (low[axis] - origin[axis]) / direction[axis];
            double last = (high[axis] - origin[axis]) / direction[axis];
            double near = Math.min(first, last), far = Math.max(first, last);
            if (near > enter) {
                enter = near;
                double sign = direction[axis] > 0 ? -1 : 1;
                normal = new HitVector(axis == 0 ? sign : 0, axis == 1 ? sign : 0, axis == 2 ? sign : 0);
            }
            exit = Math.min(exit, far);
            if (enter > exit) return Optional.empty();
        }
        return Optional.of(new HitIntersection(enter, from.add(path.multiply(enter)), normal));
    }
}
