package com.aitchn.prism.api.block;

import com.aitchn.prism.api.protection.BlockPosition;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;
import java.util.Optional;
import org.bukkit.block.Block;

public interface BlockService {
    /** Owning-region read. Empty means the block has no meaningful front; axes are not inferred as facing. */
    default Optional<BlockOrientation> orientation(Block block) {
        throw new UnsupportedOperationException("Block orientation requires API 3.3");
    }

    default Optional<BlockDirection> facing(Block block) {
        return orientation(block).map(BlockOrientation::front);
    }

    /** Captured visible ports on this block or formed structure member. Never force-loads its controller. */
    default Map<String, BlockPortConfiguration> ports(Block block) {
        throw new UnsupportedOperationException("Block ports require API 3.3");
    }

    /** Owning-region mutation. The core checks providers; legacy calls have UNKNOWN actor attribution. */
    default void configurePort(Block block, String port, BlockSide side, BlockPortMode mode) {
        throw new UnsupportedOperationException("Block ports require API 3.3");
    }

    /** Captured configurable machine ports. Legacy relocatable ports include other members;
     * fixed ports are available only on their declared member roles. */
    default Map<String, BlockPortConfiguration> configurablePorts(Block block) {
        throw new UnsupportedOperationException("Machine port positions require API 3.9");
    }

    /** Explicit position overrides only. Missing entries retain the declared structure roles. */
    default Map<String, BlockPosition> portPositions(Block block) {
        throw new UnsupportedOperationException("Machine port positions require API 3.9");
    }

    /** Assign a port to this formed member, or restore declared roles. Core authorization is required. */
    default void positionPort(Block block, String port, boolean reset) {
        throw new UnsupportedOperationException("Machine port positions require API 3.9");
    }

    /** Context-aware mutations; the implementation must not downgrade these to a legacy call. */
    default void place(Block block, PrismKey id, Map<String, String> data, boolean physics,
                       com.aitchn.prism.api.protection.ProtectionContext context) {
        restore(block, new BlockSnapshot(id, 1, data), physics, context);
    }

    default void restore(Block block, BlockSnapshot snapshot, boolean physics,
                         com.aitchn.prism.api.protection.ProtectionContext context) {
        throw new UnsupportedOperationException("Action context is unavailable");
    }

    default Optional<BlockSnapshot> breakBlock(Block block, boolean drops, boolean physics,
                                                com.aitchn.prism.api.protection.ProtectionContext context) {
        throw new UnsupportedOperationException("Action context is unavailable");
    }

    default void configurePort(Block block, String port, BlockSide side, BlockPortMode mode,
                               com.aitchn.prism.api.protection.ProtectionContext context) {
        throw new UnsupportedOperationException("Action context is unavailable");
    }

    default void positionPort(Block block, String port, boolean reset,
                              com.aitchn.prism.api.protection.ProtectionContext context) {
        throw new UnsupportedOperationException("Action context is unavailable");
    }

    Optional<BlockSnapshot> inspect(Block block);

    void place(Block block, PrismKey id, Map<String, String> data, boolean physics);

    void restore(Block block, BlockSnapshot snapshot, boolean physics);

    Optional<BlockSnapshot> breakBlock(Block block, boolean dropItem, boolean physics);
}
