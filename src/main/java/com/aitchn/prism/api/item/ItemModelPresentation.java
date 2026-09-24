package com.aitchn.prism.api.item;

import com.aitchn.prism.api.PrismKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.List;

/** Immutable client appearance. This never changes item identity or placed block presentation. */
public sealed interface ItemModelPresentation {
    PrismKey javaModel();

    /** Static source used by the Bedrock compiler; shared presentations return themselves. */
    default ItemModelPresentation bedrock() {
        return this;
    }

    /** Java item-definition entry point with an independent, static Bedrock appearance. */
    record Split(PrismKey javaModel, ItemModelPresentation bedrock, List<StateString> stateStrings,
                 Integer useProgressIndex, List<StateFloat> stateFloats, StateProfile profile)
            implements ItemModelPresentation {
        public Split {
            Objects.requireNonNull(javaModel, "javaModel");
            Objects.requireNonNull(bedrock, "bedrock");
            if (!(bedrock instanceof Resource || bedrock instanceof Sprite)) {
                throw new IllegalArgumentException("Split Bedrock presentation requires a resource or sprite");
            }
            stateStrings = List.copyOf(stateStrings);
            stateFloats = List.copyOf(stateFloats);
            if (stateStrings.size() > 64) throw new IllegalArgumentException("At most 64 state strings are supported");
            if (stateFloats.size() > 64) throw new IllegalArgumentException("At most 64 state floats are supported");
            if (useProgressIndex != null && (useProgressIndex < 0 || useProgressIndex > 255)) {
                throw new IllegalArgumentException("Use progress index must be 0..255");
            }
        }

        /** Retains the API 3.20 constructor for existing addons. */
        public Split(PrismKey javaModel, ItemModelPresentation bedrock, List<StateString> stateStrings,
                     Integer useProgressIndex, List<StateFloat> stateFloats) {
            this(javaModel, bedrock, stateStrings, useProgressIndex, stateFloats, null);
        }

        public Split(PrismKey javaModel, ItemModelPresentation bedrock, List<StateString> stateStrings,
                     Integer useProgressIndex) {
            this(javaModel, bedrock, stateStrings, useProgressIndex, List.of());
        }

        public Split(PrismKey javaModel, ItemModelPresentation bedrock, List<StateString> stateStrings) {
            this(javaModel, bedrock, stateStrings, null);
        }

        public Split(PrismKey javaModel, ItemModelPresentation bedrock) {
            this(javaModel, bedrock, List.of());
        }
    }

    /** Name-only Java profile sourced from a declared instance field, on presentation copies only. */
    record StateProfile(PrismKey nameField, String defaultName) {
        public StateProfile {
            Objects.requireNonNull(nameField, "nameField");
            if (!validName(defaultName)) throw new IllegalArgumentException("Profile default must be a Java player name");
        }

        public static boolean validName(String name) {
            return name != null && name.matches("[A-Za-z0-9_]{1,16}");
        }

        /** Malformed persisted presentation values use the snapshot-bound default. */
        public String name(ItemInstanceState state) {
            String value = state.values().getOrDefault(nameField, defaultName);
            return validName(value) ? value : defaultName;
        }
    }

    /** A numeric state field appended after static custom-model-data floats, on presentation copies only. */
    record StateFloat(PrismKey field, float defaultValue) {
        public StateFloat {
            Objects.requireNonNull(field, "field");
            if (!Float.isFinite(defaultValue)) throw new IllegalArgumentException("State float default must be finite");
        }
    }

    /** A snapshot-bound state field appended after static custom-model-data strings. */
    record StateString(PrismKey field, String defaultValue) {
        public StateString {
            Objects.requireNonNull(field, "field");
            Objects.requireNonNull(defaultValue, "defaultValue");
            if (defaultValue.length() > 4096) throw new IllegalArgumentException("State string default is too long");
        }
    }

    record Resource(PrismKey javaModel) implements ItemModelPresentation {
        public Resource {
            Objects.requireNonNull(javaModel, "javaModel");
        }
    }

    /** A single author-owned PNG compiled into both client packs. Texture keys use resource paths. */
    record Sprite(PrismKey javaModel, String texture, boolean handheld) implements ItemModelPresentation {
        public Sprite {
            Objects.requireNonNull(javaModel, "javaModel");
            if (javaModel.namespace().equals("minecraft")) {
                throw new IllegalArgumentException("Generated item models require a custom namespace");
            }
            if (texture == null || !texture.matches("[a-z0-9._-]+:[a-z0-9._/-]+")
                    || texture.endsWith(".png") || texture.contains("..") || texture.contains("//")
                    || texture.contains(":/") || texture.endsWith("/")) {
                throw new IllegalArgumentException("Texture must be a full resource key without extension or traversal");
            }
        }
    }

    /** A textures.minecraft.net skin hash, not a website record number or a player UUID. */
    record Head(String texture) implements ItemModelPresentation {
        public Head {
            if (texture == null || !texture.matches("[0-9a-f]{32,64}")) {
                throw new IllegalArgumentException("Head texture must be a lowercase Minecraft skin texture hash (32-64 hexadecimal characters)");
            }
        }

        @Override
        public PrismKey javaModel() {
            return PrismKey.parse("minecraft:player_head");
        }

        public String textureProperty() {
            String json = "{\"textures\":{\"SKIN\":{\"url\":\"https://textures.minecraft.net/texture/" + texture + "\"}}}";
            return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        }

        /** Stable synthetic profile identity prevents dynamic player-profile lookup by Java clients. */
        public java.util.UUID profileId() {
            return java.util.UUID.nameUUIDFromBytes(mappingToken().getBytes(StandardCharsets.UTF_8));
        }

        /** Presentation-only routing token shared by Java projection and the Bedrock custom item registry. */
        public String mappingToken() {
            return "prism:head/" + texture;
        }
    }
}
