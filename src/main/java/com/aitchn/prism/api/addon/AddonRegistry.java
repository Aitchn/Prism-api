package com.aitchn.prism.api.addon;

import java.util.List;
import java.util.Map;
import org.bukkit.plugin.Plugin;

/** Explicit initialization receipt. Invoke on the addon's lifecycle thread; callbacks run inline. @since 3.25 */
public interface AddonRegistry {
    /**
     * Executes synchronous initialization, records success only after normal return, and cleans owned
     * Prism registrations on failure. Exceptions/errors propagate to the caller. Zero content is valid.
     * Duplicate live owners/ids/namespaces are rejected. Reinitialize after disable with a fresh callback.
     */
    void initialize(Plugin owner, AddonDescriptor descriptor, Runnable initialization);

    /** Replaces optional/skipped reports for this initialized owner. Does not change success/failure. */
    void skippedContent(Plugin owner, Map<String, String> reasons);

    List<AddonView> snapshot();
}
