package com.aitchn.prism.api.behavior.item;

/** Pure, thread-safe presentation of validated instance state. Never access player/world state or PlaceholderAPI. */
public interface ItemPresentationBehavior extends ItemBehaviorHandler {
    ItemPresentationResult present(ItemPresentation context);
}
