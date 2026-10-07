package com.aitchn.prism.api.hit;

import java.util.concurrent.CompletionStage;
import org.bukkit.plugin.Plugin;

/** Region block queries and entity-owner snapshots are serialized by one bounded asynchronous query. */
public interface HitQueryService {
    int MAX_BLOCKS = 8192;
    int MAX_CANDIDATES = 512;
    long MAX_SNAPSHOT_AGE_MILLIS = 250;
    CompletionStage<HitQueryResult> query(Plugin owner, HitQuery query);
}
