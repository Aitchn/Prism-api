package com.aitchn.prism.api.behavior.block;

/**
 * Opts a registered container behavior into native hopper transfers and item pickup.
 * Formed structure members remain protected. This capability never overrides another
 * plugin's cancellation, ownership checks, or the PREPARE/COMMIT container dispatcher.
 * An inactive registration grants no native access.
 */
public interface BlockNativeContainerBehavior extends BlockContainerBehavior {
}
