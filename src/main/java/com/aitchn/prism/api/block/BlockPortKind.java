package com.aitchn.prism.api.block;

public enum BlockPortKind {
    ITEM, RESOURCE, SUBSTANCE;

    public static BlockPortKind parse(String value) {
        return valueOf(value.toUpperCase(java.util.Locale.ROOT));
    }
}
