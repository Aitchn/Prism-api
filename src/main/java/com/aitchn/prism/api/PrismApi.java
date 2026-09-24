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
    int API_MAJOR_VERSION = 3;
    int API_MINOR_VERSION = 21;

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

    BlockService blocks();

    ProgressService progress();

    RecipeDisplayRegistry recipeDisplays();

    MachineProcessRegistry machineProcesses();

    FishingService fishing();

    ChatTokenRegistry chatTokens();

    com.aitchn.prism.api.feedback.FeedbackService feedback();

    default boolean supports(int major, int minor) {
        return major == API_MAJOR_VERSION && minor >= 0 && minor <= API_MINOR_VERSION;
    }
}
