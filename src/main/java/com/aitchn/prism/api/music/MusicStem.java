package com.aitchn.prism.api.music;

import java.util.Objects;

/**
 * A named group of notes that can be mixed, muted, or soloed together.
 *
 * @since 3.25
 */
public record MusicStem(String id, String name) {
    public static final String MAIN = "main";

    public MusicStem {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(name, "name");
        if (!id.matches("[a-z0-9_]+")) throw new IllegalArgumentException("Stem id must match [a-z0-9_]+: " + id);
        if (name.isBlank()) throw new IllegalArgumentException("A stem needs a name");
    }
}
