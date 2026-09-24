package com.aitchn.prism.api.block;

import java.util.Locale;

/** The six orthogonal world directions; diagonal and SELF faces are deliberately absent. */
public enum BlockDirection {
    NORTH(0, 0, -1), EAST(1, 0, 0), SOUTH(0, 0, 1), WEST(-1, 0, 0), UP(0, 1, 0), DOWN(0, -1, 0);

    private final int x;
    private final int y;
    private final int z;

    BlockDirection(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int x() { return x; }
    public int y() { return y; }
    public int z() { return z; }
    public boolean horizontal() { return y == 0; }

    public BlockDirection opposite() {
        return vector(-x, -y, -z);
    }

    public static BlockDirection parse(String value) {
        return valueOf(value.toUpperCase(Locale.ROOT));
    }

    static BlockDirection vector(int x, int y, int z) {
        for (BlockDirection direction : values()) {
            if (direction.x == x && direction.y == y && direction.z == z) return direction;
        }
        throw new IllegalArgumentException("Not an orthogonal unit direction");
    }
}
