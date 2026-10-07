package com.aitchn.prism.api.hit;

import java.util.*;

/** Each strike is explicit. Different cast IDs never merge, even within the same native tick. */
public record HitIntent(HitContext context, DamageSpec damage, ShieldPolicy shield, Attribution attribution,
                        int strike, List<HitEffect> effects, HitCandidate candidate) {
    public enum Attribution { REQUIRE_NATIVE, UUID_CONTEXT_ONLY }
    public HitIntent {
        Objects.requireNonNull(context, "context"); Objects.requireNonNull(damage, "damage");
        Objects.requireNonNull(shield, "shield"); Objects.requireNonNull(attribution, "attribution"); effects = List.copyOf(effects);
        if (strike < 0 || strike > 127 || effects.size() > 16) throw new IllegalArgumentException("Hit intent budget exceeded");
        if (candidate != null && (candidate.kind() == HitCandidate.Kind.BLOCK
                || !context.target().equals(candidate.target()) || !context.world().equals(candidate.world())
                || !Objects.equals(context.part(), candidate.part()) || context.snapshotVersion() != candidate.version())) {
            throw new IllegalArgumentException("Candidate does not match hit context");
        }
        // Multipliers are only trusted when read from a current framework-owned candidate.
        if (context.part() != null && candidate == null) throw new IllegalArgumentException("Part hits require a query candidate");
        double multiplier = candidate == null ? 1 : candidate.damageMultiplier();
        if (damage.amount() * multiplier > 1_000_000) throw new IllegalArgumentException("Scaled hit exceeds damage bound");
    }
}
