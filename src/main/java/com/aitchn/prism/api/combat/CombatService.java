package com.aitchn.prism.api.combat;

import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

/** All entity operations require its Folia owner. Health persists; encounter bindings are session-scoped. */
public interface CombatService {
    HealthSnapshot health(LivingEntity entity);

    /** Sets logical base maximum, preserving the current health fraction. Range: 1..1,000,000. */
    void maximumHealth(LivingEntity entity, double maximum);

    /** Administrative assignment in logical units; does not constitute a healing event. */
    void health(LivingEntity entity, double current);

    /** Attaches an immutable profile. A different owner's existing binding is an error. */
    void enter(Plugin owner, LivingEntity entity, CombatProfile profile);

    /** Removes only the caller's encounter binding; logical health is not reset. */
    void leave(Plugin owner, LivingEntity entity);

    /** Explicit authored attack in logical units, with armor pressure in (0, 2]. */
    void damage(LivingEntity target, double amount, double pressure);

    /**
     * Explicit authored attack, including magical attacks, in logical units. Void/kill remain native.
     * Referenced source entities must share the current execution owner.
     * Ordinary environmental damage should use the native server transaction instead.
     */
    void damage(LivingEntity target, double amount, double pressure, org.bukkit.damage.DamageSource source);

    /** Native cancellable healing transaction; amount is in logical health units. */
    void heal(LivingEntity target, double amount);
}
