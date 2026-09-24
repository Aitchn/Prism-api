package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.behavior.BehaviorResult;

public interface BlockInteractBehavior extends BlockBehaviorHandler {
    BehaviorResult interact(BlockInteraction interaction);
}
