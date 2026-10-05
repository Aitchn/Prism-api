package com.aitchn.prism.api.mob.part;

import java.util.List;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;

/**
 * The hittable parts attached to one mob by one owner. Every method requires the mob's Folia owner. After
 * {@link #detach()}, the mob's removal or death, or the owner's disable, the set is detached: its parts disappear
 * from every client and further calls are ignored.
 *
 * @since 3.24
 */
public interface MobPartSet {
    Plugin owner();

    Mob mob();

    /** The parts in declaration order. */
    List<MobPart> parts();

    /**
     * Places a part's bottom centre at an offset from the mob's feet, in world axes (blocks). Offsets are limited to
     * 16 blocks on each axis. A part is not hittable until it has been placed once.
     *
     * @throws IllegalArgumentException for an unknown part or an offset out of range
     */
    void place(String part, double dx, double dy, double dz);

    /** Makes a part hittable again or not at all (for example while that limb is hidden). Parts start enabled. */
    void enabled(String part, boolean enabled);

    boolean attached();

    /** Removes every part from every client; the set stays detached. */
    void detach();
}
