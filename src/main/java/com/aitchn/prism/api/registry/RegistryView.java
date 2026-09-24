package com.aitchn.prism.api.registry;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.pack.ContentPackDescriptor;
import java.util.Map;
import java.util.Set;
import java.util.List;
import com.aitchn.prism.api.component.ComponentDefinition;
import org.bukkit.plugin.Plugin;
import com.aitchn.prism.api.mob.MobDefinition;
import com.aitchn.prism.api.mob.MobSpawnDefinition;
import java.util.Optional;
import com.aitchn.prism.api.material.MaterialDefinition;

/** Live access to committed content. Capture snapshot() before related lookups. */
public interface RegistryView extends RegistryReadView {
    RegistryReadView snapshot();

    RegistrySubscription subscribe(Plugin owner, RegistryChangeListener listener);

    void unsubscribe(Plugin owner);

    @Override
    default Set<PrismKey> items() {
        return snapshot().items();
    }

    @Override
    default Optional<com.aitchn.prism.api.item.ItemModelPresentation> itemModel(PrismKey item) {
        return snapshot().itemModel(item);
    }

    @Override
    default Set<PrismKey> blocks() {
        return snapshot().blocks();
    }

    @Override
    default Set<PrismKey> structures() {
        return snapshot().structures();
    }

    @Override
    default Set<PrismKey> machines() {
        return snapshot().machines();
    }

    @Override
    default Set<PrismKey> recipes() {
        return snapshot().recipes();
    }

    @Override
    default Set<PrismKey> research() {
        return snapshot().research();
    }

    @Override
    default Set<PrismKey> itemTags() {
        return snapshot().itemTags();
    }

    @Override
    default List<ComponentDefinition> itemComponents(PrismKey item) {
        return snapshot().itemComponents(item);
    }

    @Override
    default List<com.aitchn.prism.api.behavior.BehaviorDefinition> itemBehaviors(PrismKey item) {
        return snapshot().itemBehaviors(item);
    }

    @Override
    default Optional<String> itemNameKey(PrismKey item) {
        return snapshot().itemNameKey(item);
    }

    @Override
    default List<ComponentDefinition> blockComponents(PrismKey block) {
        return snapshot().blockComponents(block);
    }

    @Override
    default List<com.aitchn.prism.api.behavior.BehaviorDefinition> blockBehaviors(PrismKey block) {
        return snapshot().blockBehaviors(block);
    }

    @Override
    default Set<PrismKey> equipmentSets() {
        return snapshot().equipmentSets();
    }

    @Override
    default Set<PrismKey> worldGeneration() {
        return snapshot().worldGeneration();
    }

    @Override
    default Set<PrismKey> mobs() {
        return snapshot().mobs();
    }

    @Override
    default Set<PrismKey> mobSpawns() {
        return snapshot().mobSpawns();
    }

    @Override
    default Optional<MobDefinition> mob(PrismKey id) {
        return snapshot().mob(id);
    }

    @Override
    default Optional<MobSpawnDefinition> mobSpawn(PrismKey id) {
        return snapshot().mobSpawn(id);
    }

    @Override
    default Set<PrismKey> materials() {
        return snapshot().materials();
    }

    @Override
    default Optional<MaterialDefinition> material(PrismKey id) {
        return snapshot().material(id);
    }

    @Override
    default Optional<MaterialAssignment> materialForItem(PrismKey item) {
        return snapshot().materialForItem(item);
    }

    @Override
    default Map<String, ContentPackDescriptor> contentPacks() {
        return snapshot().contentPacks();
    }

    @Override
    default long revision() {
        return snapshot().revision();
    }

}
