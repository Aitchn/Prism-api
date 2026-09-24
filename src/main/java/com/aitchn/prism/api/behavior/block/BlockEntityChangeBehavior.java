package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.behavior.BehaviorResult;

/** Observes an entity changing a Prism-backed block through the native event path. */
public interface BlockEntityChangeBehavior extends BlockBehaviorHandler {
    BehaviorResult entityChange(BlockEntityChangeContext context);
}
