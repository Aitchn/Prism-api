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

    /** Privileged owning-region operation. Player-facing callers must first check protection and ownership. */
    default void configurePort(Block block, String port, BlockSide side, BlockPortMode mode) {
        throw new UnsupportedOperationException("Block ports require API 3.3");
    }

    /** All configurable ports of this machine, including ports exposed on other formed members. */
    default Map<String, BlockPortConfiguration> configurablePorts(Block block) {
        throw new UnsupportedOperationException("Machine port positions require API 3.9");
    }

    /** Explicit position overrides only. Missing entries retain the declared structure roles. */
    default Map<String, BlockPosition> portPositions(Block block) {
        throw new UnsupportedOperationException("Machine port positions require API 3.9");
    }

    /** Assign a port to this formed member, or restore declared roles when reset is true. Region-owned and privileged. */
    default void positionPort(Block block, String port, boolean reset) {
        throw new UnsupportedOperationException("Machine port positions require API 3.9");
    }

    Optional<BlockSnapshot> inspect(Block block);

    void place(Block block, PrismKey id, Map<String, String> data, boolean physics);

    void restore(Block block, BlockSnapshot snapshot, boolean physics);

    Optional<BlockSnapshot> breakBlock(Block block, boolean dropItem, boolean physics);
}
