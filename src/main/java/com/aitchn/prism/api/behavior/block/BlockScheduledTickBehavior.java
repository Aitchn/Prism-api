package com.aitchn.prism.api.behavior.block;

public interface BlockScheduledTickBehavior extends BlockBehaviorHandler {
    /**
     * Loaded-region cadence. Each interval boundary produces at most one call, which may arrive a few ticks
     * late when a chunk holds more scheduled blocks than one tick visits; unloaded ticks are never replayed.
     */
    default int intervalTicks(com.aitchn.prism.api.behavior.BehaviorOptions options) {
        int interval = options.values().containsKey("interval-ticks") ? options.requireInt("interval-ticks") : 1;
        if (interval < 1 || interval > 72_000) throw new IllegalArgumentException("Scheduled block interval must be 1..72000");
        return interval;
    }

    void scheduledTick(BlockTick tick);
}
