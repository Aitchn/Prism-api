package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/**
 * A numeric factor that forging part materials may contribute to an assembled product.
 * Contributions are summed across parts. Lore renders {@code <namespace>:forging.stat.<value>}.
 *
 * @param minimum smallest accepted single-material contribution
 * @param maximum largest accepted single-material contribution
 * @param integral whether each contribution must be a whole number
 * @param percent whether presentation multiplies the value by 100 and appends a percent sign
 * @param signed whether an unbroken product's effective value may be negative; otherwise it is clamped to zero
 */
public record ForgingStatisticType(PrismKey id, double minimum, double maximum, boolean integral,
                                   boolean percent, boolean signed) {
    public static final double LIMIT = 1_000_000;

    public ForgingStatisticType {
        Objects.requireNonNull(id, "id");
        if (!Double.isFinite(minimum) || !Double.isFinite(maximum) || minimum > maximum
                || minimum < -LIMIT || maximum > LIMIT) {
            throw new IllegalArgumentException("Forging statistic bounds must be finite, ordered and within +/-" + LIMIT);
        }
    }

    public void validate(double value) {
        if (!Double.isFinite(value) || value < minimum || value > maximum || integral && value != Math.rint(value)) {
            throw new IllegalArgumentException("Invalid forging statistic: " + id);
        }
    }
}
