package com.aitchn.prism.api.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.component.ComponentDefinition;
import com.aitchn.prism.api.component.ComponentOptions;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/** Immutable Java block presentation. Resources never constitute an item identity. */
public record BlockDisplayPresentation(PrismKey model, Orientation orientation, int rotation, PrismKey litModel,
                                       PrismKey downModel) {
    public static final PrismKey TYPE = PrismKey.parse("prism:block_display");
    public enum Orientation { FIXED, FACING, AXIS }

    /** Compatibility constructor for static presentations. litModel is null when absent. */
    public BlockDisplayPresentation(PrismKey model, Orientation orientation, int rotation) {
        this(model, orientation, rotation, null, null);
    }

    /** Compatibility constructor for API 3.15 burning-state presentations. */
    public BlockDisplayPresentation(PrismKey model, Orientation orientation, int rotation, PrismKey litModel) {
        this(model, orientation, rotation, litModel, null);
    }

    public BlockDisplayPresentation {
        Objects.requireNonNull(model);
        Objects.requireNonNull(orientation);
        if (model.namespace().equals("minecraft")) throw new IllegalArgumentException("Display models must be author-owned resources");
        if (litModel != null && litModel.namespace().equals("minecraft")) {
            throw new IllegalArgumentException("Lit display models must be author-owned resources");
        }
        if (downModel != null && downModel.namespace().equals("minecraft")) {
            throw new IllegalArgumentException("Down display models must be author-owned resources");
        }
        if (rotation < 0 || rotation > 270 || rotation % 90 != 0) {
            throw new IllegalArgumentException("Display rotation must be 0, 90, 180 or 270");
        }
    }

    public static BlockDisplayPresentation from(ComponentOptions options) {
        if (!Set.of("model", "orientation", "rotation", "lit-model", "down-model").containsAll(options.values().keySet())) {
            throw new IllegalArgumentException("Unknown block display option");
        }
        return new BlockDisplayPresentation(PrismKey.parse(options.requireString("model")),
                Orientation.valueOf(options.values().getOrDefault("orientation", "fixed").toString().toUpperCase(Locale.ROOT)),
                options.values().containsKey("rotation") ? options.requireInt("rotation") : 0,
                options.values().containsKey("lit-model") ? PrismKey.parse(options.requireString("lit-model")) : null,
                options.values().containsKey("down-model") ? PrismKey.parse(options.requireString("down-model")) : null);
    }

    public static Optional<BlockDisplayPresentation> find(List<ComponentDefinition> components) {
        return components.stream().filter(value -> value.type().equals(TYPE)).findFirst().map(value -> from(value.options()));
    }

    public static boolean supportsCarrier(String material) {
        return material.endsWith("_ORE") || material.endsWith("_LOG") || material.endsWith("_WOOD")
                || Set.of("STONE", "DEEPSLATE", "NETHERRACK", "END_STONE", "IRON_BLOCK", "GOLD_BLOCK",
                "COPPER_BLOCK", "DIAMOND_BLOCK", "NETHERITE_BLOCK", "EMERALD_BLOCK", "LAPIS_BLOCK",
                "COAL_BLOCK", "RAW_IRON_BLOCK", "RAW_GOLD_BLOCK", "RAW_COPPER_BLOCK", "BRICKS", "FURNACE",
                "BLAST_FURNACE", "SMOKER", "HOPPER").contains(material);
    }

    public void validateCarrier(String material) {
        if (!supportsCarrier(material)) throw new IllegalArgumentException("Display requires a supported full-cube carrier");
        if (litModel != null && !supportsLitState(material)) {
            throw new IllegalArgumentException("lit-model requires a FURNACE, BLAST_FURNACE or SMOKER carrier");
        }
        if (material.equals("HOPPER")) {
            if (downModel == null || orientation != Orientation.FACING || rotation != 0) {
                throw new IllegalArgumentException("HOPPER displays require down-model, facing orientation and zero rotation");
            }
        } else if (downModel != null) {
            throw new IllegalArgumentException("down-model requires a HOPPER carrier");
        }
    }

    public static boolean supportsLitState(String material) {
        return Set.of("FURNACE", "BLAST_FURNACE", "SMOKER").contains(material);
    }

    public String resource(int stage) {
        if (stage < -1 || stage > 9) throw new IllegalArgumentException("Invalid crack stage");
        return model.namespace() + ":prism_display/" + model.value() + "/" + (stage < 0 ? "clean" : "crack_" + stage);
    }
}
