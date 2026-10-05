package com.aitchn.prism.api.mob.part;

import java.util.List;
import java.util.Optional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;

/**
 * Hit volumes for mobs whose appearance is larger than their native hitbox (display-entity bosses). Each part is shown
 * only to the clients that track the mob, as a packet-only invisible interaction box: no server entity exists. A
 * player's attack on a part becomes {@link org.bukkit.entity.LivingEntity#attack} on the mob by that player, after a
 * server reach check against the part's box; a projectile whose path crosses a part is made to hit the mob. Both are
 * announced by {@link MobPartHitEvent} first and scaled by the part's damage multiplier.
 *
 * <p>Parts are approximations: axis-aligned boxes that follow the owner's {@link MobPartSet#place} calls, sent to
 * clients once per tick. The mob's native hitbox keeps working. Bedrock clients do not receive parts.</p>
 *
 * @since 3.24
 */
public interface MobPartService {
    /** At most this many parts per set. */
    int MAX_PARTS = 16;

    /**
     * Attaches parts to a mob. Requires the mob's owner. Attaching again by the same owner replaces its previous set;
     * a set held by another owner is an error.
     *
     * @throws IllegalArgumentException for no parts, more than {@link #MAX_PARTS}, or duplicate part ids
     * @throws IllegalStateException    for a disabled owner, an invalid mob, or a mob another owner holds
     */
    MobPartSet attach(Plugin owner, Mob mob, List<MobPart> parts);

    /** The attached set of a mob, if any. Requires the mob's owner. */
    Optional<MobPartSet> find(Mob mob);

    /**
     * The part through which the damage now being dispatched to {@code mob} arrived. Only meaningful inside a damage
     * event listener for that mob, on its owner; empty for any hit that did not come through a part.
     */
    Optional<MobPart> currentHit(Entity mob);
}
