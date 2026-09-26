package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;
import org.bukkit.plugin.Plugin;

/**
 * Register during addon {@code onLoad}, before content parsing. Duplicate IDs fail and never replace an
 * existing registration. A part material referencing an unregistered statistic rejects the candidate.
 */
public interface ForgingStatisticRegistry {
    void register(Plugin owner, ForgingStatisticType type);

    void unregister(Plugin owner);

    /** Immutable registered definitions, including those whose owners are not yet enabled. */
    Map<PrismKey, ForgingStatisticType> types();
}
