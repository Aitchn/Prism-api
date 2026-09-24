package com.aitchn.prism.api.mob.guide;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/** An immutable argument used inside a translated mob Guide line. */
public sealed interface MobGuideArgument
        permits MobGuideArgument.Literal, MobGuideArgument.RegistryText, MobGuideArgument.MinecraftText,
        MobGuideArgument.DifficultyValue {

    record Literal(String value) implements MobGuideArgument {
        public Literal {
            Objects.requireNonNull(value, "value");
        }
    }

    record RegistryText(PrismKey key) implements MobGuideArgument {
        public RegistryText {
            Objects.requireNonNull(key, "key");
        }
    }

    record MinecraftText(String translationKey) implements MobGuideArgument {
        public MinecraftText {
            if (translationKey == null || translationKey.isBlank()) {
                throw new IllegalArgumentException("Minecraft translation keys must be non-blank");
            }
        }
    }

    /** Values selected when the Guide is rendered in the viewer's current world. */
    record DifficultyValue(String peaceful, String easy, String normal, String hard) implements MobGuideArgument {
        public DifficultyValue {
            Objects.requireNonNull(peaceful, "peaceful");
            Objects.requireNonNull(easy, "easy");
            Objects.requireNonNull(normal, "normal");
            Objects.requireNonNull(hard, "hard");
        }
    }
}
