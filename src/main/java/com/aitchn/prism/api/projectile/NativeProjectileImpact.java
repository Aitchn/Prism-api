package com.aitchn.prism.api.projectile;

import com.aitchn.prism.api.hit.HitContext;
import com.aitchn.prism.api.hit.HitResult;
import java.util.Objects;

/** Immutable completed native-impact observation. An observer must not apply the damage again. */
public record NativeProjectileImpact(HitContext context, HitResult result) {
    public NativeProjectileImpact {
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(result, "result");
    }
}
