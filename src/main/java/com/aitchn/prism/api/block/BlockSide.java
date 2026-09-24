package com.aitchn.prism.api.block;

/** Local sides follow a block's orientation, including when a machine is rotated. */
public enum BlockSide {
    FRONT, BACK, LEFT, RIGHT, TOP, BOTTOM;

    public static BlockSide parse(String value) {
        return valueOf(value.toUpperCase(java.util.Locale.ROOT));
    }
}
