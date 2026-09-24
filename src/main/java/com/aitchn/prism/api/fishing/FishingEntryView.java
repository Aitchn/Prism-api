package com.aitchn.prism.api.fishing;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/** Guide-ready immutable view. Consumers should hide every definition field while {@link #discovered()} is false. */
public record FishingEntryView(
        PrismKey species,
        FishingSpeciesDefinition definition,
        FishingPlayerStats player,
        FishingRankings rankings,
        FishingServerRecord server,
        boolean discovered
) {
    public FishingEntryView {
        species = Objects.requireNonNull(species, "species");
        definition = Objects.requireNonNull(definition, "definition");
        player = Objects.requireNonNull(player, "player");
        rankings = Objects.requireNonNull(rankings, "rankings");
        server = Objects.requireNonNull(server, "server");
        if (discovered != player.discovered()) {
            throw new IllegalArgumentException("Fishing entry discovery flag does not match player stats");
        }
    }
}
