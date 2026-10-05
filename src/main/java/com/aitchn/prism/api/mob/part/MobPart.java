package com.aitchn.prism.api.mob.part;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * One hittable part of a mob's appearance: an axis-aligned box {@code width} wide and {@code height} tall, whose
 * bottom centre is placed each step by the owner (see {@link MobPartSet#place}). A hit on a part becomes an attack on
 * the mob; {@code damageMultiplier} scales the damage of that attack (a weak point above 1, armour below 1).
 *
 * @param id               unique within its set; lowercase {@code [a-z0-9_]+}
 * @param width            box width in blocks, 0.1..16
 * @param height           box height in blocks, 0.1..16
 * @param damageMultiplier factor applied to damage dealt through this part, 0.01..10
 * @since 3.24
 */
public record MobPart(String id, float width, float height, double damageMultiplier) {
    private static final Pattern ID = Pattern.compile("[a-z0-9_]+");

    public MobPart {
        Objects.requireNonNull(id, "id");
        if (!ID.matcher(id).matches()) throw new IllegalArgumentException("Invalid part id: " + id);
        if (!(width >= 0.1F && width <= 16F)) throw new IllegalArgumentException("Part width must be 0.1..16: " + width);
        if (!(height >= 0.1F && height <= 16F)) throw new IllegalArgumentException("Part height must be 0.1..16: " + height);
        if (!(damageMultiplier >= 0.01 && damageMultiplier <= 10)) {
            throw new IllegalArgumentException("Part damage multiplier must be 0.01..10: " + damageMultiplier);
        }
    }

    /** A part that passes damage through unchanged. */
    public MobPart(String id, float width, float height) {
        this(id, width, height, 1);
    }
}
