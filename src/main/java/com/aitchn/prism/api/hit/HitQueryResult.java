package com.aitchn.prism.api.hit;

import java.util.List;
import java.util.Objects;

/** Failure has no usable partial result. No chunk is loaded to answer a query. */
public record HitQueryResult(Status status, List<HitCandidate> candidates) {
    public enum Status { COMPLETE, WORLD_UNAVAILABLE, UNLOADED, UNSUPPORTED_REGION_BOUNDARY, BUDGET_EXCEEDED,
        STALE, TIMEOUT, OWNER_DISABLED, FAILED }
    public HitQueryResult {
        Objects.requireNonNull(status, "status"); candidates = List.copyOf(candidates);
        if (status != Status.COMPLETE && !candidates.isEmpty()) throw new IllegalArgumentException("Incomplete candidate result");
    }
    public static HitQueryResult failure(Status reason) { return new HitQueryResult(reason, List.of()); }
}
