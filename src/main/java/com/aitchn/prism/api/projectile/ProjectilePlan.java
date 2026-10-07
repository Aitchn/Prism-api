package com.aitchn.prism.api.projectile;

import com.aitchn.prism.api.hit.*;
import java.util.List;
import java.util.Objects;

/** Pure-data planning hook. No world/entity access is permitted; effects run later on the target owner. */
@FunctionalInterface
public interface ProjectilePlan {
    Outcome prepare(HitContext context, HitCandidate candidate);
    record Outcome(DamageSpec damage, ShieldPolicy shield, HitIntent.Attribution attribution, List<HitEffect> effects) {
        public Outcome {
            Objects.requireNonNull(damage, "damage"); Objects.requireNonNull(shield, "shield");
            Objects.requireNonNull(attribution, "attribution"); effects = List.copyOf(effects);
            if (effects.size() > 16) throw new IllegalArgumentException("Effect budget exceeded");
        }
    }
}
