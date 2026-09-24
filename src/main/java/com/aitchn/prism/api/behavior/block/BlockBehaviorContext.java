package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import org.bukkit.block.Block;

public interface BlockBehaviorContext {
    PrismKey blockId();

    BehaviorOptions options();

    Block block();
}
