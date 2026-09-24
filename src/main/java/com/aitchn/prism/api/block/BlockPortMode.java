package com.aitchn.prism.api.block;

/** Direction is always viewed from the configured block, never from its neighbour. */
public enum BlockPortMode {
    INPUT, OUTPUT, BOTH, NONE;

    public boolean acceptsInput() { return this == INPUT || this == BOTH; }
    public boolean permitsOutput() { return this == OUTPUT || this == BOTH; }

    public static BlockPortMode parse(String value) {
        return valueOf(value.toUpperCase(java.util.Locale.ROOT));
    }

    public boolean permits(BlockPortMode requested) {
        return (!requested.acceptsInput() || acceptsInput()) && (!requested.permitsOutput() || permitsOutput());
    }
}
