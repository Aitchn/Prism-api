package com.aitchn.prism.api.status;

import org.bukkit.entity.Player;

/** Valid only during this player-owned callback. Do not retain the player for cross-region work. */
public record StatusTickContext(Player player, StatusSnapshot status) {
}
