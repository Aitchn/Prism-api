package com.aitchn.prism.api.behavior.item;

/** Adjusts qualified mining XP, without changing items, eligibility or zero-XP drops. */
public interface ItemMiningExperienceBehavior extends ItemBehaviorHandler {
    int miningExperience(ItemMiningExperience context);
}
