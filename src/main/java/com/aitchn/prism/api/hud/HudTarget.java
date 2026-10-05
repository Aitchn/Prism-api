package com.aitchn.prism.api.hud;

import java.util.Objects;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;

/**
 * Target of a Prism HUD refresh. Live handles may only be used during the callback on
 * the owning region; retaining this value does not extend execution ownership.
 *
 * @since 3.24
 */
public sealed interface HudTarget {
    /** Captured name and health before addon presentation overrides. */
    record Entity(LivingEntity entity, Component name, double health, double maximumHealth) implements HudTarget {
        public Entity {
            Objects.requireNonNull(entity, "entity");
            Objects.requireNonNull(name, "name");
            if (!Double.isFinite(health) || health < 0 || !Double.isFinite(maximumHealth) || maximumHealth <= 0
                    || health > maximumHealth) {
                throw new IllegalArgumentException("Health must be finite and within 0..maximumHealth");
            }
        }
    }

    /** A Prism block or formed machine member, already verified as region-owned. */
    record Block(org.bukkit.block.Block block) implements HudTarget {
        public Block {
            Objects.requireNonNull(block, "block");
        }
    }
}
