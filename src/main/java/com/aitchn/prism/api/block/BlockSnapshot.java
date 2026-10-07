package com.aitchn.prism.api.block;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;

/**
 * Immutable custom block identity and data. At most 1,024 data fields are accepted; see the platform contract
 * for the encoded string length limit enforced when the state is stored.
 */
public record BlockSnapshot(PrismKey id, int dataVersion, Map<String, String> data) {
    public BlockSnapshot {
        data = Map.copyOf(data);
        if (dataVersion < 1) {
            throw new IllegalArgumentException("Custom block data versions start at 1");
        }
        if (data.size() > 1_024) {
            throw new IllegalArgumentException("Custom block data supports at most 1024 fields");
        }
    }

    public BlockSnapshot(PrismKey id) {
        this(id, 1, Map.of());
    }
}
