package com.aitchn.prism.api.mob;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import java.util.List;
import java.util.Objects;

public record MobSpawnDefinition(
        PrismKey id,
        PrismKey mob,
        PrismKey strategyType,
        BehaviorOptions strategyOptions,
        List<MobSpawnConditionDefinition> conditions,
        MobPopulationDefinition population,
        int priority
) {
    public MobSpawnDefinition {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(mob, "mob");
        Objects.requireNonNull(strategyType, "strategyType");
        strategyOptions = strategyOptions == null ? BehaviorOptions.empty() : strategyOptions;
        conditions = List.copyOf(Objects.requireNonNull(conditions, "conditions"));
        Objects.requireNonNull(population, "population");
    }
}
