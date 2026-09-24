package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.behavior.BehaviorResult;
import com.aitchn.prism.api.protection.BlockPosition;

/** Definition-bound policy for a custom carrier participating in a native beacon base.
 * Coordinates are immutable; no ownership of the base block or affected player is transferred.
 */
public interface BlockBeaconBaseBehavior extends BlockBehaviorHandler {
    BehaviorResult beaconBase(PrismKey blockId, BehaviorOptions options, BlockPosition base, BlockPosition beacon);
}
