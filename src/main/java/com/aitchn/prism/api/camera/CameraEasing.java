package com.aitchn.prism.api.camera;

/**
 * How a camera movement spreads over its ticks.
 *
 * @since 3.24
 */
public enum CameraEasing {
    /** Constant speed. */
    LINEAR,
    /** Starts slowly and speeds up. */
    EASE_IN,
    /** Starts quickly and slows to a stop. */
    EASE_OUT,
    /** Starts and ends slowly. */
    EASE_IN_OUT;

    /** The fraction of the movement done after fraction {@code t} (0..1) of its time. */
    public double apply(double t) {
        double u = Math.clamp(t, 0, 1);
        return switch (this) {
            case LINEAR -> u;
            case EASE_IN -> u * u * u;
            case EASE_OUT -> 1 - Math.pow(1 - u, 3);
            case EASE_IN_OUT -> u < 0.5 ? 4 * u * u * u : 1 - Math.pow(-2 * u + 2, 3) / 2;
        };
    }
}
