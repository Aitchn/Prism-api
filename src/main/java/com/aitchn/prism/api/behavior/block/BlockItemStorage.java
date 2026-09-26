package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.block.BlockDirection;
import java.util.Objects;
import org.bukkit.block.Block;

/**
 * One logistics access to a registered item storage (API 3.24). {@code face} is the world face of
 * {@code block} through which the caller connects, for example the face touching an industrial node.
 * Every call is made on the owning Folia region of {@code block}.
 */
public record BlockItemStorage(
        PrismKey blockId,
        BehaviorOptions options,
        Block block,
        BlockDirection face
) implements BlockBehaviorContext {
    public BlockItemStorage {
        Objects.requireNonNull(blockId, "blockId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(block, "block");
        Objects.requireNonNull(face, "face");
    }
}
