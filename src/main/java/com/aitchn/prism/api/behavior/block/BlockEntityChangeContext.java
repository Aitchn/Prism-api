package com.aitchn.prism.api.behavior.block;

import com.aitchn.prism.api.PrismKey;
import com.aitchn.prism.api.behavior.BehaviorOptions;
import com.aitchn.prism.api.behavior.BehaviorPhase;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

/**
 * Immutable observation of an entity changing a Prism-backed block.
 *
 * <p>The state strings are captured before the native event commits.  A COMMIT
 * callback therefore receives the same previous state and the event's target
 * state even when the block data has already been replaced by Minecraft.</p>
 */
public record BlockEntityChangeContext(
        PrismKey blockId,
        BehaviorOptions options,
        Block block,
        Entity entity,
        String previousState,
        String nextState,
        BehaviorPhase phase
) implements BlockBehaviorContext {
    public BlockEntityChangeContext {
        Objects.requireNonNull(blockId, "blockId");
        Objects.requireNonNull(options, "options");
        Objects.requireNonNull(block, "block");
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(previousState, "previousState");
        Objects.requireNonNull(nextState, "nextState");
        Objects.requireNonNull(phase, "phase");
    }

    public Map<String, String> previousProperties() {
        return properties(previousState);
    }

    public Map<String, String> nextProperties() {
        return properties(nextState);
    }

    public String previousProperty(String name) {
        return previousProperties().get(name);
    }

    public String nextProperty(String name) {
        return nextProperties().get(name);
    }

    public String oldState() {
        return previousState;
    }

    public String newState() {
        return nextState;
    }

    private static Map<String, String> properties(String state) {
        int start = state.indexOf('[');
        if (start < 0 || !state.endsWith("]")) {
            return Map.of();
        }
        Map<String, String> result = new LinkedHashMap<>();
        for (String entry : state.substring(start + 1, state.length() - 1).split(",")) {
            String[] pair = entry.split("=", 2);
            if (pair.length == 2 && !pair[0].isBlank()) {
                result.put(pair[0], pair[1]);
            }
        }
        return Map.copyOf(result);
    }
}
