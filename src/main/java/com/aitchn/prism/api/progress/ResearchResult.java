package com.aitchn.prism.api.progress;

import com.aitchn.prism.api.PrismKey;
import java.util.Set;

public record ResearchResult(Status status, PlayerProgressView progress, Set<PrismKey> missingPrerequisites) {
    public ResearchResult {
        missingPrerequisites = Set.copyOf(missingPrerequisites);
    }

    public enum Status {
        COMPLETED,
        ALREADY_COMPLETED,
        MISSING_PREREQUISITES
    }
}
