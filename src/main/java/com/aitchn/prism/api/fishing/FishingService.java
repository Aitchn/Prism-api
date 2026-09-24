package com.aitchn.prism.api.fishing;

import com.aitchn.prism.api.PrismKey;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/** Public fishing capability used by content, Guide, and addons. */
public interface FishingService {
    /** All registered species, including zero-weight archives. */
    Set<PrismKey> species();

    Optional<FishingSpeciesDefinition> species(PrismKey itemId);

    /** Catchable species in this biome; zero-weight archives are excluded. */
    Map<PrismKey, FishingSpeciesDefinition> speciesByBiome(PrismKey biome);

    CompletionStage<FishingPlayerProfile> load(UUID playerId);

    FishingPlayerProfile cached(UUID playerId);

    FishingServerRecord serverRecord(PrismKey species);

    FishingRankings rankings(UUID playerId, PrismKey species);

    FishingEntryView entry(UUID playerId, PrismKey species);

    /** Top catch-count rows for a species, ranked descending; includes offline profiles. */
    List<FishingCatchCountEntry> catchCountLeaderboard(PrismKey species, int limit);

    /** Top maximum-size rows for a species, ranked descending; includes offline profiles. */
    List<FishingMaximumSizeEntry> maximumSizeLeaderboard(PrismKey species, int limit);

    CompletionStage<FishingPlayerProfile> recordCatch(UUID playerId, PrismKey species,
                                                      double sizeCm, Instant caughtAt);

    /** Creates a frozen specimen item without changing any player or server records. */
    ItemStack generateSpecimen(PrismKey species, double sizeCm, Instant caughtAt);

    /** Crafts from the player's current specimen window and consumes one ordinary fish atomically. */
    CompletionStage<SpecimenResult> craftSpecimen(Player player, PrismKey species);

    /** Select without hook facts. Only unrestricted species are eligible; prefer the environment overload. */
    Optional<FishingSpeciesDefinition> chooseCustomFish(Player player, PrismKey biome, PrismKey ordinaryFish,
                                                        java.util.Random random);

    /** Select against immutable hook facts. Implementations predating API 3.20 may not support this overload. */
    default Optional<FishingSpeciesDefinition> chooseCustomFish(Player player, FishingEnvironment environment,
            PrismKey ordinaryFish, java.util.Random random) {
        throw new UnsupportedOperationException("Environment-aware fishing selection requires API 3.20");
    }
}
