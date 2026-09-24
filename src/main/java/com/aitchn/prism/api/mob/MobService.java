package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface MobService {
    CompletionStage<LivingEntity> spawn(MobSpawnRequest request);

    Optional<PrismKey> id(Entity entity);

    Optional<MobDefinition> definition(Entity entity);
}
