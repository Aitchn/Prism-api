package com.aitchn.prism.api.hit;

import java.util.*;
import java.util.concurrent.CompletionStage;
import org.bukkit.plugin.Plugin;

/** Shared native hit transaction. Framework commits damage once; addon callbacks must not apply it again. */
public interface HitService {
    Set<ShieldPolicy.Kind> supportedShieldPolicies();
    HitCast begin(Plugin owner);
    interface HitCast extends AutoCloseable {
        UUID id();
        /** Same hitId returns the same result; another hit against target/strike is a duplicate. */
        CompletionStage<HitResult> submit(HitIntent intent);
        boolean active();
        /** Revokes queued hits. Already committed native state and addon side effects cannot be rolled back. */
        @Override void close();
    }
}
