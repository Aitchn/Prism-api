package com.aitchn.prism.api.protection;

/** A public mutation was refused before its authoritative write. */
public final class ProtectionDeniedException extends IllegalStateException {
    public ProtectionDeniedException(String reason) { super(reason); }
}
