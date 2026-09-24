package com.aitchn.prism.api.machine;

/** Expected input incompatibility, optionally displayed using the supplied locale key. */
public final class MachineProcessRejection extends IllegalArgumentException {
    private final String messageKey;

    public MachineProcessRejection(String messageKey) {
        super(messageKey);
        this.messageKey = java.util.Objects.requireNonNull(messageKey, "messageKey");
    }

    public String messageKey() {
        return messageKey;
    }
}
