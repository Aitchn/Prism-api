package com.aitchn.prism.api.input;

import java.util.Map;
import java.util.UUID;

/** Only stateful sources appear. Missing means unknown, never false/released. */
public record InputSnapshot(UUID playerId, long epoch, InputContext context, Map<InputSource, InputEvent> states) {
    public InputSnapshot { states = Map.copyOf(states); }
}
