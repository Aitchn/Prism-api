package com.aitchn.prism.api.behavior.item;

/** COMMIT-only effects. Deferred work must check context.active() and entity ownership again. */
public interface ItemHitBehavior extends ItemBehaviorHandler {
    void hit(ItemHit context);
}
