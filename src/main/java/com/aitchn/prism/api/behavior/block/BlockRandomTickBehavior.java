package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.behavior.BehaviorOptions;

public interface BlockRandomTickBehavior extends BlockBehaviorHandler {
    default double randomTickChance(BehaviorOptions options) {
        double chance = options.value("random-tick-chance")
                .map(value -> {
                    if (!(value instanceof Number number)) {
                        throw new IllegalArgumentException("random-tick-chance must be a number");
                    }
                    return number.doubleValue();
                })
                .orElse(1.0D);
        if (!Double.isFinite(chance) || chance <= 0.0D || chance > 1.0D) {
            throw new IllegalArgumentException("random-tick-chance must be greater than 0 and at most 1");
        }
        return chance;
    }

    void randomTick(BlockTick tick);
}
