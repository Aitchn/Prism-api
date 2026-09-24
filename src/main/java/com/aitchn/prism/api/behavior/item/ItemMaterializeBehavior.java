package com.aitchn.prism.api.behavior.item;

/** Derives native item authority from validated instance state without reading player or world state. */
@FunctionalInterface
public interface ItemMaterializeBehavior extends ItemBehaviorHandler {
    void materialize(ItemMaterialization context);
}
