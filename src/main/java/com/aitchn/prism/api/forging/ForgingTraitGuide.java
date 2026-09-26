package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Objects;

/**
 * Guide text for a trait. Keys are full locale keys, normally in the trait owner's namespace; arguments are
 * pre-formatted literals referenced as {@code {0}}, {@code {1}}, ... in the translation.
 *
 * @param description key of the description line
 * @param arguments literals for the description line
 * @param tier key of one tier line, or {@code null} to omit the tier table. Each tier line receives the Roman
 *             requirement as {@code {0}}, {@link ForgingTraitEffect#formatTierValue} of its value as {@code {1}},
 *             then {@code tierArguments}
 * @param tierArguments literals appended to every tier line
 */
public record ForgingTraitGuide(PrismKey description, List<String> arguments, PrismKey tier, List<String> tierArguments) {
    public ForgingTraitGuide {
        Objects.requireNonNull(description, "description");
        arguments = List.copyOf(arguments);
        tierArguments = List.copyOf(tierArguments);
        if (arguments.size() > 16 || tierArguments.size() > 16) {
            throw new IllegalArgumentException("Trait guide lines accept at most 16 arguments");
        }
    }

    public ForgingTraitGuide(PrismKey description, List<String> arguments) {
        this(description, arguments, null, List.of());
    }
}
