package com.aitchn.prism.api.input;

import java.util.Optional;

/** Any-thread close is idempotent and nonblocking. No new delivery is claimed after close;
 * an already claimed/running callback may complete. Never wait across Folia owners. */
public interface InputSubscription extends AutoCloseable {
    boolean active();
    InputSnapshot snapshot();
    Optional<InputResetReason> endReason();
    @Override void close();
}
