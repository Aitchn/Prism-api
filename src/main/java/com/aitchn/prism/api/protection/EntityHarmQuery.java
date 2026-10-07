package com.aitchn.prism.api.protection;

import com.aitchn.prism.api.hit.HitContext;
import java.util.Objects;

/** Immutable target-owner query for damage and effect-only skills. Source UUID is not native killer credit. */
public record EntityHarmQuery(HitContext context, boolean damage, boolean effects) {
    public EntityHarmQuery { Objects.requireNonNull(context, "context"); }
}
