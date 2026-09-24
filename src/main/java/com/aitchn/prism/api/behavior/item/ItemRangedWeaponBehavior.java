package com.aitchn.prism.api.behavior.item;

import java.util.Optional;

public interface ItemRangedWeaponBehavior extends ItemBehaviorHandler {
    Optional<RangedWeaponProfile> rangedWeapon(ItemProjectileProfile context);
}
