package com.aitchn.prism.api.registry;

/** An idempotently closeable listener registration. Closing an old handle never removes a replacement. */
public interface RegistrySubscription extends AutoCloseable {
    boolean isActive();

    @Override
    void close();
}
