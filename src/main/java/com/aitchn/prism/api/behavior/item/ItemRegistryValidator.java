package com.aitchn.prism.api.behavior.item;

/** Optional capability for behavior validation that needs the complete item registry. */
public interface ItemRegistryValidator extends ItemBehaviorHandler {
    void validateRegistry(ItemBehaviorRegistryValidationContext context);
}
