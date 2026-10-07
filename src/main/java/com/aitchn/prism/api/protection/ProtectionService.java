package com.aitchn.prism.api.protection;

import java.util.List;
import org.bukkit.plugin.Plugin;

public interface ProtectionService {
    void register(Plugin owner, ProtectionProvider provider);

    void unregister(Plugin owner);

    List<ProtectionProvider> providers();

    ProtectionDecision query(ProtectionQuery query);

    /** Capability check; API version alone does not establish this unpublished extension. */
    default boolean supportsActionContext() { return false; }

    /** Optional unpublished native managed-wheat settlement capability; no implicit fallback. */
    default boolean supportsNativeCropSettlement() { return false; }

    default ProtectionTransaction transaction(ProtectionQuery query) {
        if (!supportsActionContext()) throw new UnsupportedOperationException("Action context is unavailable");
        return new ProtectionTransaction(this, query);
    }

    /** Preserve the merged shield API's explicit entity-harm support requirement. */
    default ProtectionDecision queryEntityHarm(EntityHarmQuery query) {
        return ProtectionDecision.deny("protection.prism.entity-harm-unsupported");
    }
}
