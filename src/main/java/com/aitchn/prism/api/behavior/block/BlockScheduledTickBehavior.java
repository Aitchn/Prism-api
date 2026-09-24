package com.aitchn.prism.api.behavior.block;

public interface BlockScheduledTickBehavior extends BlockBehaviorHandler {
    /** Loaded-region cadence; missed/unloaded ticks are never replayed. */
    default int intervalTicks(com.aitchn.prism.api.behavior.BehaviorOptions options) {
        int interval = options.values().containsKey("interval-ticks") ? options.requireInt("interval-ticks") : 1;
        if (interval < 1 || interval > 72_000) throw new IllegalArgumentException("Scheduled block interval must be 1..72000");
        return interval;
    }

    void scheduledTick(BlockTick tick);
}
