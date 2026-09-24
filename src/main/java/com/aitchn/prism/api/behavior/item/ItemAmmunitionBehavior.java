package com.aitchn.prism.api.behavior.item;

import java.util.Optional;

public interface ItemAmmunitionBehavior extends ItemBehaviorHandler {
    Optional<RangedAmmunitionProfile> ammunition(ItemProjectileProfile context);
}
