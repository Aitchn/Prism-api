package com.aitchn.prism.api.worldgen;

import com.aitchn.prism.api.PrismKey;
import java.util.Set;
import java.util.Optional;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import org.bukkit.plugin.Plugin;

public interface WorldGeneratorRegistry {
    default void register(Plugin owner, PrismKey id, WorldGenerator generator) {
        register(owner, id, WorldGenerationValidator.acceptingAnyOptions(), generator,
                WorldGenerationInformationProvider.none());
    }

    default void register(
            Plugin owner,
            PrismKey id,
            WorldGenerationValidator validator,
            WorldGenerator generator
    ) {
        register(owner, id, validator, generator, WorldGenerationInformationProvider.none());
    }

    void register(
            Plugin owner,
            PrismKey id,
            WorldGenerationValidator validator,
            WorldGenerator generator,
            WorldGenerationInformationProvider information
    );

    Optional<WorldGenerationInformation> information(PrismKey type, BehaviorOptions options);

    void unregister(Plugin owner);

    Set<PrismKey> registeredTypes();
}
