package com.aitchn.prism.api.hit;

import java.util.Objects;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

/** Target-owner cancellable prepare notification. Do not retain entity/event references. */
public final class HitPrepareEvent extends EntityEvent implements org.bukkit.event.Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final HitIntent intent;
    private boolean cancelled;
    public HitPrepareEvent(LivingEntity target, HitIntent intent) {
        super(Objects.requireNonNull(target, "target"));
        this.intent = Objects.requireNonNull(intent, "intent");
    }
    @Override public LivingEntity getEntity() { return (LivingEntity) entity; }
    public HitIntent getIntent() { return intent; }
    @Override public boolean isCancelled() { return cancelled; }
    @Override public void setCancelled(boolean value) { cancelled = value; }
    @Override public HandlerList getHandlers() { return HANDLERS; }
    public static HandlerList getHandlerList() { return HANDLERS; }
}
