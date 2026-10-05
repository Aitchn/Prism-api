package com.aitchn.prism.api.registry;

import com.aitchn.prism.api.PrismKey;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Detached immutable expanded logical structure; no runtime bindings or world state. @since 3.25 */
public record StructurePreview(PrismKey id, List<Cell> cells, int minimumRepetitions,
        int maximumRepetitions, boolean movableController, int repetitions, int belowController) {
    public StructurePreview {
        Objects.requireNonNull(id);
        cells = List.copyOf(cells);
        if (minimumRepetitions < 0 || maximumRepetitions < minimumRepetitions
                || repetitions < minimumRepetitions || repetitions > maximumRepetitions
                || belowController < 0 || belowController > repetitions
                || (!movableController && belowController != 0))
            throw new IllegalArgumentException("Invalid structure expansion");
    }
    /** Alternatives are one requirement, not one required item of every alternative. */
    public record Cell(int x, int y, int z, Set<PrismKey> alternatives,
            String role, boolean air, Map<String, String> states) {
        public Cell {
            alternatives = Set.copyOf(alternatives);
            states = Map.copyOf(states);
            Objects.requireNonNull(role);
            if (alternatives.isEmpty() != air || air && (!states.isEmpty() || !role.equals("interior")))
                throw new IllegalArgumentException("Invalid air/material requirement");
        }
    }
    /** Counts requirements by alternative set. Required air is excluded; layers do not alter totals. */
    public Map<Set<PrismKey>, Long> materialTotals() {
        return cells.stream().filter(cell -> !cell.air()).collect(java.util.stream.Collectors.toUnmodifiableMap(
                Cell::alternatives, cell -> 1L, Long::sum));
    }
}
