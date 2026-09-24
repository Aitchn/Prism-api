package com.aitchn.prism.api.behavior.item;

public interface ItemDefinitionValidator extends ItemBehaviorHandler {
    void validateDefinition(ItemBehaviorValidationContext context);
}
