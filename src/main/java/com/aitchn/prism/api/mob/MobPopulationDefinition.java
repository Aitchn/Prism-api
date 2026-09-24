package com.aitchn.prism.api.mob;

public record MobPopulationDefinition(
        int minimumGroupSize,
        int maximumGroupSize,
        int maximumPerRegion,
        int maximumNearPlayer
) {
    public MobPopulationDefinition {
        if (minimumGroupSize < 1 || maximumGroupSize < minimumGroupSize) {
            throw new IllegalArgumentException("Mob spawn group sizes must be positive and ordered");
        }
        if (maximumPerRegion < 1 || maximumNearPlayer < 1) {
            throw new IllegalArgumentException("Mob population limits must be positive");
        }
    }

    public static MobPopulationDefinition single() {
        return new MobPopulationDefinition(1, 1, 32, 16);
    }
}
