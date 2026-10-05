package com.aitchn.prism.api.mob.part;

import java.util.Objects;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

/**
 * Called on the mob's owner before a hit on one of its parts turns into an attack on the mob: a player's melee attack
 * on the part ({@link #getProjectile()} is null) or a projectile whose path crossed it. Cancelling drops the hit; the
 * attack or projectile hit that follows raises the usual damage events, during which
 * {@link MobPartService#currentHit} reports this part. Do not retain this event.
 *
 * @since 3.24
 */
public final class MobPartHitEvent extends EntityEvent implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final MobPart part;
    private final Entity attacker;
    private final Projectile projectile;
    private boolean cancelled;

    public MobPartHitEvent(Mob mob, MobPart part, Entity attacker, Projectile projectile) {
        super(Objects.requireNonNull(mob, "mob"));
        this.part = Objects.requireNonNull(part, "part");
        this.attacker = attacker;
        this.projectile = projectile;
    }

    @Override public Mob getEntity() { return (Mob) entity; }

    public MobPart getPart() { return part; }

    /** The attacking player, or the projectile's shooter (null when it has none). */
    public Entity getAttacker() { return attacker; }

    /** The projectile for a ranged hit, null for melee. */
    public Projectile getProjectile() { return projectile; }

    @Override public boolean isCancelled() { return cancelled; }

    @Override public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

    @Override public HandlerList getHandlers() { return HANDLERS; }

    public static HandlerList getHandlerList() { return HANDLERS; }
}
