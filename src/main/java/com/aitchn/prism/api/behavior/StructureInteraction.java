package com.aitchn.prism.api.behavior;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.client.ClientProfile;
import java.util.Map;
import java.util.UUID;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public record StructureInteraction(
        Player player,
        ClientProfile client,
        Block controller,
        UUID instanceId,
        PrismKey structureId,
        Map<String, Object> options,
        org.bukkit.inventory.EquipmentSlot hand
) {
    public StructureInteraction {
        options = Map.copyOf(options);
        java.util.Objects.requireNonNull(hand, "hand");
    }

    public StructureInteraction(Player player, ClientProfile client, Block controller, UUID instanceId,
                                 PrismKey structureId, Map<String, Object> options) {
        this(player, client, controller, instanceId, structureId, options, org.bukkit.inventory.EquipmentSlot.HAND);
    }
}
