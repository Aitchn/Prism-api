package com.aitchn.prism.api.mob.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;

public record MobGuideLine(PrismKey text, List<MobGuideArgument> arguments) {
    public MobGuideLine {
        Objects.requireNonNull(text, "text");
        arguments = List.copyOf(Objects.requireNonNull(arguments, "arguments"));
    }

    public MobGuideLine(PrismKey text, MobGuideArgument... arguments) {
        this(text, List.of(arguments));
    }
}
