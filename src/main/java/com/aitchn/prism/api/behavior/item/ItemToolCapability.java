package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;
import java.util.Set;

public record ItemToolCapability(
        int harvestLevel,
        Set<PrismKey> families,
        PrismKey repairItem,
        double repairPerItem
) {
    public ItemToolCapability {
        if (harvestLevel < 0) {
            throw new IllegalArgumentException("Tool harvest level must be non-negative");
        }
        families = Set.copyOf(families);
        if (families.isEmpty()) {
            throw new IllegalArgumentException("A tool capability requires at least one family");
        }
        if (repairItem == null && repairPerItem != 0.0D) {
            throw new IllegalArgumentException("A repair amount requires a repair item");
        }
        if (repairItem != null && (!Double.isFinite(repairPerItem)
                || repairPerItem <= 0.0D || repairPerItem > 1.0D)) {
            throw new IllegalArgumentException("Tool repair-per-item must be greater than 0 and at most 1");
        }
        Objects.requireNonNull(families, "families");
    }
}
