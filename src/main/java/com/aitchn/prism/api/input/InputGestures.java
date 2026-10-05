package com.aitchn.prism.api.input;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;

/** Optional recognition above sources, with no feature binding or native-action policy. */
public final class InputGestures {
    private InputGestures() { }
    /**
     * One recognizer per subscription. Use with InputOptions.withHolds(). Fires once after an observed BEGIN
     * and elapsed owner-tick HOLD samples. Existing-held SNAPSHOT does not fabricate a fresh press.
     * Close discards this recognizer; RESET/END cancels it. This is not a physical mouse long-press detector.
     */
    public static InputListener hold(InputSource source, Duration minimum, Consumer<InputEvent> action) {
        Objects.requireNonNull(source); Objects.requireNonNull(minimum); Objects.requireNonNull(action);
        if (source != InputSource.JUMP && source != InputSource.SNEAK && source != InputSource.SPRINT && source != InputSource.USE_STATE)
            throw new IllegalArgumentException("Hold requires a stateful boolean or native-use source");
        long threshold = minimum.toNanos();
        if (threshold <= 0) throw new IllegalArgumentException("Hold duration must be positive");
        return new InputListener() {
            boolean armed, fired;
            long started, epoch;
            java.util.UUID player;
            @Override public InputPropagation onInput(InputEvent event) {
                if (event.phase() == InputPhase.RESET) { armed = false; fired = false; return InputPropagation.CONTINUE; }
                if (event.source() != source) return InputPropagation.CONTINUE;
                if (event.phase() == InputPhase.BEGIN) {
                    started = event.observedNanos(); epoch = event.epoch(); player = event.playerId(); armed = true; fired = false;
                } else if (event.phase() == InputPhase.END || event.phase() == InputPhase.SNAPSHOT) {
                    armed = false; fired = false;
                } else if (event.phase() == InputPhase.HOLD && armed && !fired && event.epoch() == epoch
                        && event.playerId().equals(player) && event.observedNanos() - started >= threshold) {
                    fired = true; action.accept(event);
                }
                return InputPropagation.CONTINUE;
            }
        };
    }
}
