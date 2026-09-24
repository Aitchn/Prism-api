package com.aitchn.prism.api.worldgen;

import com.aitchn.prism.api.PrismKey;

public interface WorldGenerationContext {
    PrismKey definitionId();

    PrismKey world();

    int chunkX();

    int chunkZ();

    int minimumY();

    int maximumY();

    PrismKey blockAt(int x, int y, int z);

    PrismKey biomeAt(int x, int y, int z);

    int highestBlockYAt(int x, int z);

    int nextInt(int bound);

    int nextInt(int origin, int bound);

    double nextDouble();
}
