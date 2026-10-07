package com.aitchn.prism.api.hit;

import java.util.Objects;
import java.util.function.Consumer;
import org.bukkit.entity.LivingEntity;

/** Callback is invoked at most once on the target's entity owner, after native damage has returned. */
public record HitEffect(Gate gate, Consumer<Context> action) {
    public enum Gate { ON_DAMAGE, ON_UNBLOCKED, ON_BLOCK, ON_CONTACT }
    public record Context(LivingEntity target, HitContext hit, HitResult result) { }
    public HitEffect { Objects.requireNonNull(gate, "gate"); Objects.requireNonNull(action, "action"); }
}
