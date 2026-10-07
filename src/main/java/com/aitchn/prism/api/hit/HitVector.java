package com.aitchn.prism.api.hit;

/** Immutable finite world-space vector. Positions and simulation data never retain Bukkit objects. */
public record HitVector(double x, double y, double z) {
    public static final HitVector ZERO = new HitVector(0, 0, 0);
    public HitVector {
        for (double value : new double[]{x, y, z}) {
            if (!Double.isFinite(value) || Math.abs(value) > 60_000_000) {
                throw new IllegalArgumentException("Vector components must be finite and within 60,000,000");
            }
        }
    }
    public HitVector add(HitVector other) { return new HitVector(x + other.x, y + other.y, z + other.z); }
    public HitVector subtract(HitVector other) { return new HitVector(x - other.x, y - other.y, z - other.z); }
    public HitVector multiply(double value) { return new HitVector(x * value, y * value, z * value); }
    public double length() { return Math.sqrt(x * x + y * y + z * z); }
    public double dot(HitVector other) { return x * other.x + y * other.y + z * other.z; }
    public HitVector normalized() { double size = length(); return size == 0 ? ZERO : multiply(1 / size); }
}
