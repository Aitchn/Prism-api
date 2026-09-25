package com.aitchn.prism.api.status;

/** Effect logic runs only on the affected player's entity scheduler. */
@FunctionalInterface
public interface StatusBehavior {
    void tick(StatusTickContext context);
}
