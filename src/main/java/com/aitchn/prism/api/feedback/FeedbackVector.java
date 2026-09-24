package com.aitchn.prism.api.feedback;

/** Immutable finite coordinates, offsets or velocity. */
public record FeedbackVector(double x, double y, double z) {
    public static final FeedbackVector ZERO = new FeedbackVector(0, 0, 0);
    public FeedbackVector {
        if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)) {
            throw new IllegalArgumentException("Feedback coordinates must be finite");
        }
    }
    public FeedbackVector add(FeedbackVector other) { return new FeedbackVector(x + other.x, y + other.y, z + other.z); }
    public FeedbackVector scale(double scale) { return new FeedbackVector(x * scale, y * scale, z * scale); }
    public double distanceSquared(FeedbackVector other) {
        double dx = x - other.x, dy = y - other.y, dz = z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }
}
