package com.aitchn.prism.api;

import com.aitchn.prism.api.behavior.StructureBehaviorRegistry;
import com.aitchn.prism.api.client.ClientService;
import com.aitchn.prism.api.protection.ProtectionService;
import com.aitchn.prism.api.registry.RegistryView;
import com.aitchn.prism.api.item.ItemService;
import com.aitchn.prism.api.block.BlockService;
import com.aitchn.prism.api.progress.ProgressService;
import com.aitchn.prism.api.behavior.item.ItemBehaviorRegistry;
import com.aitchn.prism.api.behavior.block.BlockBehaviorRegistry;
import com.aitchn.prism.api.component.ComponentRegistry;
import com.aitchn.prism.api.worldgen.WorldGeneratorRegistry;
import com.aitchn.prism.api.mob.MobBehaviorRegistry;
import com.aitchn.prism.api.mob.MobControllerRegistry;
import com.aitchn.prism.api.mob.MobSpawnConditionRegistry;
import com.aitchn.prism.api.mob.MobSpawnStrategyRegistry;
import com.aitchn.prism.api.mob.MobService;
import com.aitchn.prism.api.mob.guide.MobGuideRegistry;
import com.aitchn.prism.api.recipe.RecipeDisplayRegistry;
import com.aitchn.prism.api.item.guide.ItemGuideRegistry;
import com.aitchn.prism.api.machine.MachineProcessRegistry;
import com.aitchn.prism.api.fishing.FishingService;
import com.aitchn.prism.api.chat.ChatTokenRegistry;

public interface PrismApi {
    /** Shared logical health and opt-in encounter defense for players and mobs. */
    default com.aitchn.prism.api.combat.CombatService combat() {
        throw new UnsupportedOperationException("This Prism implementation does not provide combat profiles");
    }
    /** Hit-enabled 0.9.63/API 3.25 build; older 3.25 implementations may lack these services. */
    default com.aitchn.prism.api.hit.HitboxService hitboxes() {
        throw new UnsupportedOperationException("This Prism implementation does not provide generic hitboxes");
    }
    default com.aitchn.prism.api.hit.HitQueryService hitQueries() {
        throw new UnsupportedOperationException("This Prism implementation does not provide hit queries");
    }
    default com.aitchn.prism.api.hit.HitService hits() {
        throw new UnsupportedOperationException("This Prism implementation does not provide hit transactions");
    }
    default com.aitchn.prism.api.projectile.ProjectileService projectiles() {
        throw new UnsupportedOperationException("This Prism implementation does not provide virtual projectiles");
    }

    int API_MAJOR_VERSION = 3;
    int API_MINOR_VERSION = 25;

    /**
     * Owner-bound semantic input observations; no implicit gameplay capture.
     * Added in the input-enabled 0.9.63 build of API 3.25. Earlier 3.25 builds lack this method;
     * addons must check service availability in addition to the API version.
     * @since 3.25
     */
    default com.aitchn.prism.api.input.InputService inputs() {
        throw new UnsupportedOperationException("This Prism implementation does not provide input observations");
    }

    /** Explicit addon identity and initialization results. @since 3.25 */
    default com.aitchn.prism.api.addon.AddonRegistry addons() {
        throw new UnsupportedOperationException("This Prism implementation does not provide addon status");
    }

    RegistryView registries();

    StructureBehaviorRegistry structureBehaviors();

    ItemBehaviorRegistry itemBehaviors();

    BlockBehaviorRegistry blockBehaviors();

    ComponentRegistry components();

    WorldGeneratorRegistry worldGenerators();

    MobBehaviorRegistry mobBehaviors();

    MobControllerRegistry mobControllers();

    MobSpawnStrategyRegistry mobSpawnStrategies();

    MobSpawnConditionRegistry mobSpawnConditions();

    MobService mobs();

    MobGuideRegistry mobGuide();

    ItemGuideRegistry itemGuide();

    ClientService clients();

    ProtectionService protections();

    ItemService items();

    /**
     * Stored energy of {@code prism:energy_storage} items. Added in the engine-enabled 0.9.63 build of
     * API 3.25; earlier 3.25 builds lack this service, so addons must check its availability.
     *
     * @since 3.25
     */
    default com.aitchn.prism.api.item.EnergyService energy() {
        throw new UnsupportedOperationException("This Prism implementation does not provide energy items");
    }

    BlockService blocks();

    ProgressService progress();

    RecipeDisplayRegistry recipeDisplays();

    MachineProcessRegistry machineProcesses();

    FishingService fishing();

    ChatTokenRegistry chatTokens();

    /** Experimental online status framework; Prism registers no default effects. */
    default com.aitchn.prism.api.status.StatusService statuses() {
        throw new UnsupportedOperationException("This Prism implementation does not provide statuses");
    }

    com.aitchn.prism.api.feedback.FeedbackService feedback();

    /** Assembled equipment queries and the product, trait and statistic registries. */
    default com.aitchn.prism.api.forging.ForgingService forging() {
        throw new UnsupportedOperationException("This Prism implementation does not provide forging");
    }

    /**
     * Packet-only hit parts for mobs whose appearance is larger than their native hitbox.
     *
     * @since 3.24
     */
    default com.aitchn.prism.api.mob.part.MobPartService mobParts() {
        throw new UnsupportedOperationException("This Prism implementation does not provide mob parts");
    }

    /**
     * Server-controlled music played from resource-pack audio.
     *
     * @since 3.24
     */
    default com.aitchn.prism.api.music.MusicService music() {
        throw new UnsupportedOperationException("This Prism implementation does not provide music");
    }

    /**
     * Server-controlled cameras for Java players: anchored views the player looks around from, and directed shots.
     *
     * @since 3.24
     */
    default com.aitchn.prism.api.camera.CameraService cameras() {
        throw new UnsupportedOperationException("This Prism implementation does not provide cameras");
    }

    default boolean supports(int major, int minor) {
        return major == API_MAJOR_VERSION && minor >= 0 && minor <= API_MINOR_VERSION;
    }
}
