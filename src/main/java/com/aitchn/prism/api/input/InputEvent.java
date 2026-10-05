package com.aitchn.prism.api.input;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.UUID;

/** Delivered on the player's entity scheduler. IDs are ordered within one channel epoch only. */
public record InputEvent(UUID playerId, long epoch, long eventId, long observedNanos,
                         InputSource source, InputPhase phase, InputOrigin origin, InputEvidence evidence,
                         OptionalInt protocolSequence, boolean cancelledAtObservation,
                         InputContext context, InputPayload payload) {
    public InputEvent {
        Objects.requireNonNull(playerId); Objects.requireNonNull(source); Objects.requireNonNull(phase);
        Objects.requireNonNull(origin); Objects.requireNonNull(evidence); Objects.requireNonNull(protocolSequence);
        Objects.requireNonNull(context); Objects.requireNonNull(payload);
    }
}
