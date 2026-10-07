package com.aitchn.prism.api.projectile;

import java.util.Objects;

/** Java ItemDisplay item-model resource path; visual scale never changes collision dimensions. */
public record ProjectileAppearance(String itemModel, float scale, int viewDistance) {
    public ProjectileAppearance {
        Objects.requireNonNull(itemModel, "itemModel");
        if (!itemModel.matches("[a-z0-9._-]+:[a-z0-9._/-]+") || itemModel.length() > 256
                || !Float.isFinite(scale) || scale < .01F || scale > 16 || viewDistance < 4 || viewDistance > 128) {
            throw new IllegalArgumentException("Invalid projectile appearance");
        }
    }
}
