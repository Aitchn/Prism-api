package com.aitchn.prism.api.worldgen;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public record WorldGenerationInformation(
        Set<PrismKey> generatedBlocks,
        Set<PrismKey> dimensions,
        List<HeightRange> heightRanges
) {
    private static final WorldGenerationInformation EMPTY =
            new WorldGenerationInformation(Set.of(), Set.of(), List.of());

    public WorldGenerationInformation {
        generatedBlocks = Set.copyOf(Objects.requireNonNull(generatedBlocks, "generatedBlocks"));
        dimensions = Set.copyOf(Objects.requireNonNull(dimensions, "dimensions"));
        heightRanges = List.copyOf(Objects.requireNonNull(heightRanges, "heightRanges"));
    }

    public static WorldGenerationInformation empty() {
        return EMPTY;
    }

    public boolean isEmpty() {
        return generatedBlocks.isEmpty() && dimensions.isEmpty() && heightRanges.isEmpty();
    }

    public record HeightRange(int minimumY, int maximumY) {
        public HeightRange {
            if (minimumY > maximumY) {
                throw new IllegalArgumentException("Minimum generation height cannot exceed maximum height");
            }
        }
    }
}
