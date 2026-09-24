package com.aitchn.prism.api.fishing;

import java.util.Objects;
import java.util.Optional;
import org.bukkit.inventory.ItemStack;

/** Result of an atomic specimen attempt. A failed result never consumes an ordinary fish or window record. */
public record SpecimenResult(Status status, Optional<ItemStack> specimen, Optional<FishingCatch> catchRecord) {
    public SpecimenResult {
        status = Objects.requireNonNull(status, "status");
        specimen = Objects.requireNonNull(specimen, "specimen").map(ItemStack::clone);
        catchRecord = Objects.requireNonNull(catchRecord, "catchRecord");
        if (status == Status.CREATED && (specimen.isEmpty() || catchRecord.isEmpty())) {
            throw new IllegalArgumentException("Created specimen results require an item and catch record");
        }
        if (status != Status.CREATED && (specimen.isPresent() || catchRecord.isPresent())) {
            throw new IllegalArgumentException("Failed specimen results cannot contain output data");
        }
    }

    /** Return an outbound copy so callers cannot mutate the result retained by this immutable value. */
    @Override
    public Optional<ItemStack> specimen() {
        return specimen.map(ItemStack::clone);
    }

    public enum Status {
        CREATED,
        UNKNOWN_SPECIES,
        NOT_DISCOVERED,
        NO_NEW_CATCH,
        MISSING_FISH,
        INVENTORY_FULL,
        FAILED
    }
}
