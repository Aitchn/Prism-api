package com.aitchn.prism.api.block;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;

public record BlockSnapshot(PrismKey id, int dataVersion, Map<String, String> data) {
    public BlockSnapshot {
        data = Map.copyOf(data);
        if (dataVersion < 1) {
            throw new IllegalArgumentException("Custom block data versions start at 1");
        }
    }

    public BlockSnapshot(PrismKey id) {
        this(id, 1, Map.of());
    }
}
