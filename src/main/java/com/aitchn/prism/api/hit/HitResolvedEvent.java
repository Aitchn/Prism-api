package com.aitchn.prism.api.hit;

import java.util.Objects;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

/** Target-owner read-only resolution notification. Do not retain entity/event references. */
public final class HitResolvedEvent extends EntityEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final HitContext context;
    private final HitResult result;
    public HitResolvedEvent(LivingEntity target, HitContext context, HitResult result) {
        super(Objects.requireNonNull(target, "target"));
        this.context = Objects.requireNonNull(context, "context"); this.result = Objects.requireNonNull(result, "result");
    }
    @Override public LivingEntity getEntity() { return (LivingEntity) entity; }
    public HitContext getContext() { return context; }
    public HitResult getResult() { return result; }

    @Override public HandlerList getHandlers() { return HANDLERS; }
    public static HandlerList getHandlerList() { return HANDLERS; }
}
