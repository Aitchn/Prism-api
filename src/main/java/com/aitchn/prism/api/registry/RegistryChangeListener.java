package com.aitchn.prism.api.registry;

@FunctionalInterface
public interface RegistryChangeListener {
    void activated(RegistryReadView registry);
}
