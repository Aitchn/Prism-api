package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.behavior.BehaviorResult;

public interface BlockContainerBehavior extends BlockBehaviorHandler {
    BehaviorResult container(BlockContainerInteraction interaction);
}
