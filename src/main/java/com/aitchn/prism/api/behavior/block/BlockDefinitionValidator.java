package com.aitchn.prism.api.behavior.block;

public interface BlockDefinitionValidator extends BlockBehaviorHandler {
    void validateDefinition(BlockBehaviorValidationContext context);
}
