package com.aitchn.prism.api.block;

import java.util.Objects;

/** Immutable right-handed orientation. Top must be perpendicular to front. */
public record BlockOrientation(BlockDirection front, BlockDirection top) {
    public BlockOrientation {
        Objects.requireNonNull(front, "front");
        Objects.requireNonNull(top, "top");
        if (front == top || front == top.opposite()) {
            throw new IllegalArgumentException("Front and top must be perpendicular");
        }
    }

    /** Vertical native blocks use south as the top of an upward front and north for a downward front. */
    public static BlockOrientation facing(BlockDirection front) {
        return new BlockOrientation(front, switch (front) {
            case UP -> BlockDirection.SOUTH;
            case DOWN -> BlockDirection.NORTH;
            default -> BlockDirection.UP;
        });
    }

    public BlockDirection world(BlockSide side) {
        BlockDirection right = BlockDirection.vector(
                front.y() * top.z() - front.z() * top.y(),
                front.z() * top.x() - front.x() * top.z(),
                front.x() * top.y() - front.y() * top.x());
        return switch (side) {
            case FRONT -> front;
            case BACK -> front.opposite();
            case TOP -> top;
            case BOTTOM -> top.opposite();
            case RIGHT -> right;
            case LEFT -> right.opposite();
        };
    }

    public BlockSide local(BlockDirection direction) {
        Objects.requireNonNull(direction, "direction");
        for (BlockSide side : BlockSide.values()) {
            if (world(side) == direction) return side;
        }
        throw new IllegalStateException("Incomplete orientation");
    }
}
