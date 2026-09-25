package com.aitchn.prism.api.machine;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.block.BlockPortMode;
import com.aitchn.prism.api.block.BlockSide;
import com.aitchn.prism.api.component.ComponentDefinition;
import com.aitchn.prism.api.component.ComponentOptions;
import com.aitchn.prism.api.data.OptionValues;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Java-only presentation parts for a formed machine. The structure and its
 * carrier blocks remain authoritative for collision and persistence; these
 * parts are visual resources projected by the Java display session.
 */
public record MachineDisplayPresentation(List<Part> parts, boolean replaceAnchors,
                                         java.util.Map<String, Vector> feedbackAnchors, int animationCycleTicks,
                                         boolean controllerOnly, boolean strikeProfile) {
    public static final PrismKey TYPE = PrismKey.parse("prism:machine_display");
    private static final int MAX_PARTS = 8;
    private static final int MAX_SURFACES = 256;
    private static final int MIN_ANIMATION_CYCLE_TICKS = 2;
    private static final int MAX_ANIMATION_CYCLE_TICKS = 1200;
    private static final Pattern PART_ID = Pattern.compile("[a-z0-9._-]+");
    private static final Set<String> MACHINE_KEYS = Set.of("parts", "replace-anchors", "feedback-anchors", "animation-cycle-ticks", "controller-only", "strike-profile");
    private static final Set<String> PART_KEYS = Set.of("id", "model", "offset", "scale", "travel", "port-surfaces", "rotation", "rotation-travel", "process-models", "output-model", "output-storage", "motion-keyframes", "process-window");

    public MachineDisplayPresentation(List<Part> parts, boolean replaceAnchors,
            java.util.Map<String, Vector> anchors, int cycle) {
        this(parts, replaceAnchors, anchors, cycle, false, false);
    }

    /** Existing additive presentations retain their construction-block visuals. */
    public MachineDisplayPresentation(List<Part> parts) {
        this(parts, false);
    }

    public MachineDisplayPresentation(List<Part> parts, boolean replaceAnchors) {
        this(parts, replaceAnchors, java.util.Map.of(), 0);
    }

    public MachineDisplayPresentation(List<Part> parts, boolean replaceAnchors, java.util.Map<String, Vector> feedbackAnchors) {
        this(parts, replaceAnchors, feedbackAnchors, 0);
    }

    public MachineDisplayPresentation(List<Part> parts, boolean replaceAnchors, int animationCycleTicks) {
        this(parts, replaceAnchors, java.util.Map.of(), animationCycleTicks);
    }

    public MachineDisplayPresentation {
        feedbackAnchors = java.util.Map.copyOf(feedbackAnchors);
        if (feedbackAnchors.size() > 16) throw new IllegalArgumentException("Machine display supports at most 16 feedback anchors");
        feedbackAnchors.forEach((name, point) -> {
            if (!PART_ID.matcher(name).matches()) throw new IllegalArgumentException("Invalid machine feedback anchor: " + name);
            Part.validateBounded(point, "feedback-anchor", -8, 8);
        });
        if (animationCycleTicks != 0
                && (animationCycleTicks < MIN_ANIMATION_CYCLE_TICKS
                || animationCycleTicks > MAX_ANIMATION_CYCLE_TICKS)) {
            throw new IllegalArgumentException("Machine display animation-cycle-ticks must be 0 or "
                    + MIN_ANIMATION_CYCLE_TICKS + ".." + MAX_ANIMATION_CYCLE_TICKS);
        }
        if (strikeProfile && animationCycleTicks == 0) throw new IllegalArgumentException("Strike profile requires a cycle");
        Objects.requireNonNull(parts, "parts");
        if (parts.isEmpty() || parts.size() > MAX_PARTS) {
            throw new IllegalArgumentException("Machine display requires 1.." + MAX_PARTS + " parts");
        }
        var ids = new HashSet<String>();
        for (Part part : parts) {
            Objects.requireNonNull(part, "parts cannot contain null");
            if (!ids.add(part.id())) {
                throw new IllegalArgumentException("Machine display part IDs must be unique: " + part.id());
            }
        }
        if (parts.stream().mapToInt(part -> part.surfaces().size()).sum() > MAX_SURFACES) {
            throw new IllegalArgumentException("Machine display supports at most " + MAX_SURFACES + " port surfaces");
        }
        parts = List.copyOf(parts);
    }

    public static MachineDisplayPresentation from(ComponentOptions options) {
        Objects.requireNonNull(options, "options");
        if (!options.values().containsKey("parts")
                || !MACHINE_KEYS.containsAll(options.values().keySet())) {
            throw new IllegalArgumentException("Machine display options require parts and optional replace-anchors"
                    + ", feedback-anchors or animation-cycle-ticks");
        }
        Object replacement = options.values().getOrDefault("replace-anchors", false);
        if (!(replacement instanceof Boolean replaceAnchors)) {
            throw new IllegalArgumentException("Machine display replace-anchors must be a boolean");
        }
        int animationCycleTicks = options.values().containsKey("animation-cycle-ticks")
                ? animationCycleTicks(options.requireInt("animation-cycle-ticks")) : 0;
        Object raw = options.values().get("parts");
        if (!(raw instanceof List<?> values)) {
            throw new IllegalArgumentException("Machine display parts must be a list");
        }
        if (values.isEmpty() || values.size() > MAX_PARTS) {
            throw new IllegalArgumentException("Machine display requires 1.." + MAX_PARTS + " parts");
        }
        var parts = new ArrayList<Part>(values.size());
        for (Object value : values) {
            if (!(value instanceof java.util.Map<?, ?> rawPart)) {
                throw new IllegalArgumentException("Machine display parts must be maps");
            }
            if (!rawPart.keySet().stream().allMatch(String.class::isInstance)
                    || !PART_KEYS.containsAll(rawPart.keySet().stream().map(String.class::cast).toList())
                    || !rawPart.keySet().containsAll(Set.of("id", "model"))) {
                throw new IllegalArgumentException("Machine display part has invalid keys; id and model are required");
            }
            Object idValue = rawPart.get("id");
            Object modelValue = rawPart.get("model");
            if (!(idValue instanceof String id) || id.isBlank() || !PART_ID.matcher(id).matches()) {
                throw new IllegalArgumentException("Machine display part id must match [a-z0-9._-]+");
            }
            if (!(modelValue instanceof String model)) {
                throw new IllegalArgumentException("Machine display part model must be a string");
            }
            PrismKey modelKey = PrismKey.parse(model);
            if (modelKey.namespace().equals("minecraft")) {
                throw new IllegalArgumentException("Machine display models must be author-owned resources");
            }
            parts.add(new Part(id, modelKey,
                    vector(rawPart, "offset", new Vector(0, 0, 0), true),
                    vector(rawPart, "scale", new Vector(1, 1, 1), false),
                    vector(rawPart, "travel", new Vector(0, 0, 0), true),
                    surfaces(rawPart.get("port-surfaces")),
                    vector(rawPart, "rotation", new Vector(0, 0, 0), true),
                    vector(rawPart, "rotation-travel", new Vector(0, 0, 0), true),
                    stages(rawPart.get("process-models")), optionalModel(rawPart.get("output-model")),
                    rawPart.containsKey("output-storage") ? string(rawPart, "output-storage") : "",
                    motion(rawPart.get("motion-keyframes")), window(rawPart.get("process-window"))));
        }
        var anchors = new java.util.LinkedHashMap<String, Vector>();
        if (options.values().containsKey("feedback-anchors")) {
            if (!(options.values().get("feedback-anchors") instanceof java.util.Map<?, ?> points)) {
                throw new IllegalArgumentException("Machine feedback anchors must be a mapping");
            }
            for (var entry : points.entrySet()) {
                if (!(entry.getKey() instanceof String name)) throw new IllegalArgumentException("Machine feedback anchor names must be strings");
                anchors.put(name, vector(java.util.Map.of(name, entry.getValue()), name, new Vector(0, 0, 0), true));
            }
        }
        return new MachineDisplayPresentation(parts, replaceAnchors, anchors, animationCycleTicks,
                bool(options.values(), "controller-only"), bool(options.values(), "strike-profile"));
    }

    private static boolean bool(java.util.Map<String, Object> options, String key) {
        Object value = options.getOrDefault(key, false);
        if (!(value instanceof Boolean result)) throw new IllegalArgumentException(key + " must be boolean");
        return result;
    }

    private static PrismKey optionalModel(Object value) {
        if (value == null) return null;
        if (!(value instanceof String text)) throw new IllegalArgumentException("Expected model ID");
        var key = PrismKey.parse(text);
        if (key.namespace().equals("minecraft")) throw new IllegalArgumentException("Model must be author-owned");
        return key;
    }

    private static List<Stage> stages(Object raw) {
        if (raw == null) return List.of();
        if (!(raw instanceof List<?> list) || list.isEmpty() || list.size() > 8)
            throw new IllegalArgumentException("process-models requires 1..8 stages");
        var result = new ArrayList<Stage>();
        double previous = -1;
        for (Object value : list) {
            var map = exactMap(value, Set.of("from", "model"), "process model");
            if (!(map.get("from") instanceof Number number)) throw new IllegalArgumentException("Stage from must be numeric");
            double from = number.doubleValue();
            if (!Double.isFinite(from) || from < 0 || from >= 1 || from <= previous)
                throw new IllegalArgumentException("Stage thresholds must increase in [0,1)");
            var model = optionalModel(map.get("model"));
            if (model == null) throw new IllegalArgumentException("Stage model required");
            result.add(new Stage(from, model)); previous = from;
        }
        if (result.getFirst().from() != 0) throw new IllegalArgumentException("First stage must start at zero");
        return List.copyOf(result);
    }

    public record Stage(double from, PrismKey model) {
        public Stage {
            if (!Double.isFinite(from) || from < 0 || from >= 1 || model == null || model.namespace().equals("minecraft"))
                throw new IllegalArgumentException("Invalid process model stage");
        }
    }

    /** A normalized committed-recipe position and its linear travel multiplier. */
    public record MotionKeyframe(double at, double value) {
        public MotionKeyframe {
            if (!Double.isFinite(at) || !Double.isFinite(value) || at < 0 || at > 1 || value < 0 || value > 1)
                throw new IllegalArgumentException("Motion keyframes require finite values in [0,1]");
        }
    }

    private static List<MotionKeyframe> motion(Object raw) {
        if (raw == null) return List.of();
        if (!(raw instanceof List<?> values) || values.isEmpty()) throw new IllegalArgumentException("Invalid motion-keyframes");
        var frames = new ArrayList<MotionKeyframe>();
        for (var value : values) {
            var map = exactMap(value, Set.of("at", "value"), "motion keyframe");
            frames.add(new MotionKeyframe(OptionValues.requireDouble(map.get("at"), "at"),
                    OptionValues.requireDouble(map.get("value"), "value")));
        }
        return validateMotion(frames);
    }

    private static List<MotionKeyframe> validateMotion(List<MotionKeyframe> frames) {
        frames = List.copyOf(frames);
        if (frames.isEmpty()) return frames;
        if (frames.size() < 2 || frames.size() > 16 || frames.getFirst().at() != 0 || frames.getLast().at() != 1)
            throw new IllegalArgumentException("motion-keyframes requires 2..16 frames spanning 0..1");
        for (int i = 1; i < frames.size(); i++) if (frames.get(i).at() <= frames.get(i - 1).at())
            throw new IllegalArgumentException("Motion keyframe positions must increase");
        return frames;
    }

    private static List<Double> window(Object raw) {
        if (raw == null) return List.of();
        if (!(raw instanceof List<?> values) || values.isEmpty()) throw new IllegalArgumentException("Invalid process-window");
        return validateWindow(values.stream().map(value -> OptionValues.requireDouble(value, "process-window")).toList());
    }

    private static List<Double> validateWindow(List<Double> values) {
        values = List.copyOf(values);
        if (!values.isEmpty() && (values.size() != 2 || !Double.isFinite(values.get(0)) || !Double.isFinite(values.get(1))
                || values.get(0) < 0 || values.get(1) > 1 || values.get(0) >= values.get(1)))
            throw new IllegalArgumentException("process-window requires [start,end] with 0 <= start < end <= 1");
        return values;
    }

    private static int animationCycleTicks(int value) {
        if (value < MIN_ANIMATION_CYCLE_TICKS || value > MAX_ANIMATION_CYCLE_TICKS) {
            throw new IllegalArgumentException("Machine display animation-cycle-ticks must be "
                    + MIN_ANIMATION_CYCLE_TICKS + ".." + MAX_ANIMATION_CYCLE_TICKS);
        }
        return value;
    }

    private static List<PortSurface> surfaces(Object raw) {
        if (raw == null) return List.of();
        if (!(raw instanceof List<?> values) || values.isEmpty() || values.size() > MAX_SURFACES) {
            throw new IllegalArgumentException("Machine display port-surfaces must contain 1.." + MAX_SURFACES + " entries");
        }
        var result = new ArrayList<PortSurface>();
        for (var value : values) {
            var surface = exactMap(value, Set.of("id", "cell", "side", "closed-model", "variants"), "port surface");
            if (!(surface.get("cell") instanceof List<?> coordinates) || coordinates.size() != 3) {
                throw new IllegalArgumentException("Machine display port surface cell requires three integers");
            }
            var cell = new Cell(OptionValues.requireInt(coordinates.get(0), "port-surface.cell.x"),
                    OptionValues.requireInt(coordinates.get(1), "port-surface.cell.y"),
                    OptionValues.requireInt(coordinates.get(2), "port-surface.cell.z"));
            if (!(surface.get("variants") instanceof List<?> variants) || variants.isEmpty() || variants.size() > 8) {
                throw new IllegalArgumentException("Machine display port surface requires 1..8 variants");
            }
            var bindings = new ArrayList<PortVariant>();
            for (var entry : variants) {
                var variant = exactMap(entry, Set.of("port", "mode", "model"), "port variant");
                bindings.add(new PortVariant(string(variant, "port"), BlockPortMode.parse(string(variant, "mode")),
                        PrismKey.parse(string(variant, "model"))));
            }
            result.add(new PortSurface(string(surface, "id"), cell, BlockSide.parse(string(surface, "side")),
                    PrismKey.parse(string(surface, "closed-model")), bindings));
        }
        return List.copyOf(result);
    }

    private static java.util.Map<?, ?> exactMap(Object raw, Set<String> keys, String name) {
        if (!(raw instanceof java.util.Map<?, ?> map) || !map.keySet().equals(keys)) {
            throw new IllegalArgumentException("Machine display " + name + " requires exactly " + keys);
        }
        return map;
    }

    private static String string(java.util.Map<?, ?> map, String key) {
        if (!(map.get(key) instanceof String value) || value.isBlank()) {
            throw new IllegalArgumentException("Machine display " + key + " must be a non-empty string");
        }
        return value;
    }

    private static Vector vector(java.util.Map<?, ?> part, String key, Vector fallback, boolean boundedOffset) {
        if (!part.containsKey(key)) return fallback;
        Object raw = part.get(key);
        if (!(raw instanceof List<?> values) || values.size() != 3) {
            throw new IllegalArgumentException("Machine display " + key + " must be a three-number vector");
        }
        double[] result = new double[3];
        for (int i = 0; i < 3; i++) {
            result[i] = OptionValues.requireDouble(values.get(i), "machine_display." + key);
            if (boundedOffset && Math.abs(result[i]) > (key.startsWith("rotation") ? 180 : 8)) {
                throw new IllegalArgumentException("Machine display " + key + " must stay within +/-8 blocks");
            }
            if (!boundedOffset && !(result[i] > 0 && result[i] <= 8)) {
                throw new IllegalArgumentException("Machine display scale values must be >0 and <=8");
            }
        }
        return new Vector(result[0], result[1], result[2]);
    }

    public static Optional<MachineDisplayPresentation> find(List<ComponentDefinition> components) {
        Objects.requireNonNull(components, "components");
        return components.stream().filter(value -> value.type().equals(TYPE)).findFirst()
                .map(value -> from(value.options()));
    }

    public record Part(String id, PrismKey model, Vector offset, Vector scale, Vector travel,
                       List<PortSurface> surfaces, Vector rotation, Vector rotationTravel,
                       List<Stage> processModels, PrismKey outputModel, String outputStorage,
                       List<MotionKeyframe> motionKeyframes, List<Double> processWindow) {
        public Part(String id, PrismKey model, Vector offset, Vector scale, Vector travel,
                    List<PortSurface> surfaces, Vector rotation, Vector rotationTravel,
                    List<Stage> processModels, PrismKey outputModel, String outputStorage) {
            this(id, model, offset, scale, travel, surfaces, rotation, rotationTravel,
                    processModels, outputModel, outputStorage, List.of(), List.of());
        }
        public Part(String id, PrismKey model, Vector offset, Vector scale, Vector travel, List<PortSurface> surfaces) {
            this(id, model, offset, scale, travel, surfaces, new Vector(0,0,0), new Vector(0,0,0), List.of(), null, "");
        }
        public Part(String id, PrismKey model, Vector offset, Vector scale, Vector travel) {
            this(id, model, offset, scale, travel, List.of());
        }

        public Part {
            motionKeyframes = validateMotion(motionKeyframes);
            processWindow = validateWindow(processWindow);
            Objects.requireNonNull(id, "id");
            Objects.requireNonNull(model, "model");
            Objects.requireNonNull(offset, "offset");
            Objects.requireNonNull(scale, "scale");
            Objects.requireNonNull(travel, "travel");
            if (id.isBlank() || !PART_ID.matcher(id).matches()) {
                throw new IllegalArgumentException("Machine display part id must match [a-z0-9._-]+");
            }
            if (model.namespace().equals("minecraft")) {
                throw new IllegalArgumentException("Machine display models must be author-owned resources");
            }
            validateBounded(offset, "offset", -8, 8);
            validateBounded(travel, "travel", -8, 8);
            validateBounded(scale, "scale", Double.MIN_VALUE, 8);
            validateBounded(rotation, "rotation", -180, 180);
            validateBounded(rotationTravel, "rotation-travel", -180, 180);
            processModels = List.copyOf(processModels);
            if (processModels.size() > 8 || !processModels.isEmpty() && processModels.getFirst().from() != 0)
                throw new IllegalArgumentException("Invalid process model stages");
            for (int i = 1; i < processModels.size(); i++) if (processModels.get(i).from() <= processModels.get(i-1).from())
                throw new IllegalArgumentException("Process model stages must increase");
            if ((outputModel == null) != outputStorage.isEmpty() || !outputStorage.isEmpty() && !PART_ID.matcher(outputStorage).matches()
                    || outputModel != null && outputModel.namespace().equals("minecraft"))
                throw new IllegalArgumentException("Output model requires a storage group");
            surfaces = List.copyOf(surfaces);
            if (!surfaces.isEmpty() && (!motionKeyframes.isEmpty() || !processWindow.isEmpty() || !processModels.isEmpty() || outputModel != null
                    || !rotation.equals(new Vector(0,0,0)) || !rotationTravel.equals(new Vector(0,0,0))))
                throw new IllegalArgumentException("Port surfaces cannot use process models or rotating parts");
            if (surfaces.size() > MAX_SURFACES
                    || surfaces.stream().map(PortSurface::id).distinct().count() != surfaces.size()) {
                throw new IllegalArgumentException("Machine display port surface IDs must be unique and bounded");
            }
            if (!surfaces.isEmpty() && !travel.equals(new Vector(0, 0, 0))) {
                throw new IllegalArgumentException("Machine port surfaces must belong to a stationary part");
            }
        }

        private static void validateBounded(Vector value, String name, double minimum, double maximum) {
            for (double component : value.values()) {
                if (!Double.isFinite(component) || component < minimum || component > maximum
                        || (name.equals("scale") && component <= 0)) {
                    throw new IllegalArgumentException("Machine display " + name + " has an invalid value");
                }
            }
        }

        public String resource() {
            return model.namespace() + ":prism_machine/" + model.value();
        }
    }

    /** A fixed member face; each variant is bound to an actual logical machine port. */
    public record PortSurface(String id, Cell cell, BlockSide side, PrismKey closedModel,
                              List<PortVariant> variants) {
        public PortSurface {
            Objects.requireNonNull(id, "id");
            Objects.requireNonNull(cell, "cell");
            Objects.requireNonNull(side, "side");
            Objects.requireNonNull(closedModel, "closedModel");
            if (!PART_ID.matcher(id).matches() || closedModel.namespace().equals("minecraft")) {
                throw new IllegalArgumentException("Invalid machine port surface identity or closed model");
            }
            variants = List.copyOf(variants);
            if (variants.isEmpty() || variants.size() > 8
                    || variants.stream().map(PortVariant::token).distinct().count() != variants.size()) {
                throw new IllegalArgumentException("Machine port variants must be unique and contain 1..8 entries");
            }
        }
    }

    public record PortVariant(String port, BlockPortMode mode, PrismKey model) {
        public PortVariant {
            Objects.requireNonNull(port, "port");
            Objects.requireNonNull(mode, "mode");
            Objects.requireNonNull(model, "model");
            if (!PART_ID.matcher(port).matches() || mode == BlockPortMode.NONE || model.namespace().equals("minecraft")) {
                throw new IllegalArgumentException("Machine port variants require an active mode and author-owned model");
            }
        }

        public String token() { return port + "/" + mode.name().toLowerCase(Locale.ROOT); }
    }

    public record Cell(int x, int y, int z) {
        public Cell {
            if (Math.abs((long) x) > 8 || Math.abs((long) y) > 8 || Math.abs((long) z) > 8) {
                throw new IllegalArgumentException("Machine display port cells must stay within +/-8 blocks");
            }
        }
    }

    public record Vector(double x, double y, double z) {
        public Vector {
            if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)) {
                throw new IllegalArgumentException("Machine display vectors must be finite");
            }
        }

        private double[] values() {
            return new double[]{x, y, z};
        }
    }
}
