package com.aitchn.prism.api.projectile;

/** Immutable notifications. Scheduler context is deliberately unspecified; callbacks must not read Bukkit state. */
public interface ProjectileObserver {
    ProjectileObserver NONE = new ProjectileObserver() { };
    default void launched(ProjectileSnapshot snapshot) { }
    default void stepped(ProjectileSnapshot snapshot) { }
    default void removed(ProjectileSnapshot snapshot) { }
}
