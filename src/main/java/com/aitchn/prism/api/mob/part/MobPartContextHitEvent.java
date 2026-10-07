package com.aitchn.prism.api.mob.part;

import com.aitchn.prism.api.hit.HitContext;
import java.util.Objects;
import org.bukkit.entity.Mob;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityEvent;

/** New generic/virtual hit bridge. The legacy null-projectile melee convention is unchanged. */
public final class MobPartContextHitEvent extends EntityEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final MobPart part;
    private final HitContext context;
    private boolean cancelled;
    public MobPartContextHitEvent(Mob target, MobPart part, HitContext context) {
        super(Objects.requireNonNull(target, "target")); this.part = Objects.requireNonNull(part, "part");
        this.context = Objects.requireNonNull(context, "context");
    }
    @Override public Mob getEntity() { return (Mob) entity; }
    public MobPart getPart() { return part; }
    public HitContext getContext() { return context; }
    @Override public boolean isCancelled() { return cancelled; }
    @Override public void setCancelled(boolean value) { cancelled = value; }
    @Override public HandlerList getHandlers() { return HANDLERS; }
    public static HandlerList getHandlerList() { return HANDLERS; }
}
