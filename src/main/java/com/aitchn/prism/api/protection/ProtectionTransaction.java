package com.aitchn.prism.api.protection;

import java.util.Objects;
import java.util.function.BooleanSupplier;

/**
 * One owner-thread commit attempt, not a cached grant or a cross-region atomic transaction.
 * The caller supplies current native veto and instance/ownership validity checks.
 * Providers run immediately before the mutation; the checks run again after provider callbacks.
 */
public final class ProtectionTransaction {
    private final ProtectionService service;
    private final ProtectionQuery query;
    private boolean attempted;

    public ProtectionTransaction(ProtectionService service, ProtectionQuery query) {
        this.service = Objects.requireNonNull(service);
        this.query = Objects.requireNonNull(query);
    }

    public boolean commit(BooleanSupplier valid, BooleanSupplier nativeAllowed, Runnable mutation) {
        Objects.requireNonNull(valid); Objects.requireNonNull(nativeAllowed); Objects.requireNonNull(mutation);
        if (attempted) throw new IllegalStateException("An action commit may be attempted only once");
        attempted = true;
        if (!nativeAllowed.getAsBoolean() || !valid.getAsBoolean()) return false;
        if (service.query(query).denied()) return false;
        if (!nativeAllowed.getAsBoolean() || !valid.getAsBoolean()) return false;
        mutation.run();
        return true;
    }
}
