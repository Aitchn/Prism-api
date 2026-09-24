package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.behavior.BehaviorPhase;
import java.util.Objects;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public final class BlockContainerInteraction implements BlockBehaviorContext {
    private final PrismKey blockId;
    private final BehaviorOptions options;
    private final Block block;
    private final BlockContainerAction action;
    private final ItemStack item;
    private final BehaviorPhase phase;
    private final boolean durationSupported;
    private int durationTicks;

    public BlockContainerInteraction(
            PrismKey blockId,
            BehaviorOptions options,
            Block block,
            BlockContainerAction action,
            ItemStack item
    ) {
        this(blockId, options, block, action, item, BehaviorPhase.PREPARE, -1, false);
    }

    public BlockContainerInteraction(
            PrismKey blockId,
            BehaviorOptions options,
            Block block,
            BlockContainerAction action,
            ItemStack item,
            int durationTicks
    ) {
        this(blockId, options, block, action, item, BehaviorPhase.PREPARE, durationTicks, true);
    }

    public BlockContainerInteraction(
            PrismKey blockId,
            BehaviorOptions options,
            Block block,
            BlockContainerAction action,
            ItemStack item,
            BehaviorPhase phase
    ) {
        this(blockId, options, block, action, item, phase, -1, false);
    }

    private BlockContainerInteraction(
            PrismKey blockId,
            BehaviorOptions options,
            Block block,
            BlockContainerAction action,
            ItemStack item,
            BehaviorPhase phase,
            int durationTicks,
            boolean durationSupported
    ) {
        this.blockId = Objects.requireNonNull(blockId, "blockId");
        this.options = Objects.requireNonNull(options, "options");
        this.block = Objects.requireNonNull(block, "block");
        this.action = Objects.requireNonNull(action, "action");
        this.item = item;
        this.phase = Objects.requireNonNull(phase, "phase");
        this.durationSupported = durationSupported;
        if (durationSupported && durationTicks < 0) {
            throw new IllegalArgumentException("Container duration cannot be negative");
        }
        this.durationTicks = durationTicks;
    }

    @Override
    public PrismKey blockId() {
        return blockId;
    }

    @Override
    public BehaviorOptions options() {
        return options;
    }

    @Override
    public Block block() {
        return block;
    }

    public BlockContainerAction action() {
        return action;
    }

    public ItemStack item() {
        return item;
    }

    public BehaviorPhase phase() {
        return phase;
    }

    public boolean supportsDurationTicks() {
        return durationSupported;
    }

    public int durationTicks() {
        if (!durationSupported) {
            throw new IllegalStateException("This container action has no duration");
        }
        return durationTicks;
    }

    public void durationTicks(int durationTicks) {
        if (!durationSupported) {
            throw new IllegalStateException("This container action has no duration");
        }
        if (durationTicks < 0) {
            throw new IllegalArgumentException("Container duration cannot be negative");
        }
        this.durationTicks = durationTicks;
    }
}
