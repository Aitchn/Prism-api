package com.aitchn.prism.api.mob;

import java.util.Objects;
import org.bukkit.entity.LivingEntity;

public record MobAttackContext(MobContext mob, LivingEntity target, double damage) {
    public MobAttackContext {
        Objects.requireNonNull(mob, "mob");
        Objects.requireNonNull(target, "target");
        if (!Double.isFinite(damage) || damage < 0.0D) {
            throw new IllegalArgumentException("Mob attack damage must be finite and non-negative");
        }
    }
}
