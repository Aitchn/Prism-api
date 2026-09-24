package com.aitchn.prism.api.registry;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.pack.ContentPackDescriptor;
import java.util.Map;
import java.util.Set;
import java.util.List;
import com.aitchn.prism.api.component.ComponentDefinition;
import com.aitchn.prism.api.mob.MobDefinition;
import com.aitchn.prism.api.mob.MobSpawnDefinition;
import java.util.Optional;
import com.aitchn.prism.api.material.MaterialDefinition;
import com.aitchn.prism.api.material.EquipmentType;

/** A thread-safe immutable read of a single content snapshot. */
public interface RegistryReadView {
    Set<PrismKey> items();

    /** Configured item appearance only; independent of placed block appearance and canonical identity. */
    default Optional<com.aitchn.prism.api.item.ItemModelPresentation> itemModel(PrismKey item) {
        return Optional.empty();
    }

    Set<PrismKey> blocks();

    Set<PrismKey> structures();

    Set<PrismKey> machines();

    Set<PrismKey> recipes();

    Set<PrismKey> research();

    Set<PrismKey> itemTags();

    default List<ComponentDefinition> itemComponents(PrismKey item) {
        return List.of();
    }

    default List<com.aitchn.prism.api.behavior.BehaviorDefinition> itemBehaviors(PrismKey item) {
        return List.of();
    }

    /** Locale key only; rendering must use the captured language/viewer context. */
    default Optional<String> itemNameKey(PrismKey item) {
        return Optional.empty();
    }

    default List<ComponentDefinition> blockComponents(PrismKey block) {
        return List.of();
    }

    /** Immutable configured bindings only, without executable handler objects. */
    default List<com.aitchn.prism.api.behavior.BehaviorDefinition> blockBehaviors(PrismKey block) {
        return List.of();
    }

    default Set<PrismKey> equipmentSets() {
        return Set.of();
    }

    default Set<PrismKey> worldGeneration() {
        return Set.of();
    }

    default Set<PrismKey> mobs() {
        return Set.of();
    }

    default Set<PrismKey> mobSpawns() {
        return Set.of();
    }

    default Optional<MobDefinition> mob(PrismKey id) {
        return Optional.empty();
    }

    default Optional<MobSpawnDefinition> mobSpawn(PrismKey id) {
        return Optional.empty();
    }

    default Set<PrismKey> materials() {
        return Set.of();
    }

    default Optional<MaterialDefinition> material(PrismKey id) {
        return Optional.empty();
    }

    default Optional<MaterialAssignment> materialForItem(PrismKey item) {
        return Optional.empty();
    }

    Map<String, ContentPackDescriptor> contentPacks();

    /**
     * Committed content revision, or -1 for a candidate/detached behavior read.
     * A negative revision must not be used as a shared cache key; retain that view by identity instead.
     */
    long revision();

    record MaterialAssignment(PrismKey material, EquipmentType equipmentType) {
    }
}
