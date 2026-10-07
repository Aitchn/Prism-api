package com.aitchn.prism.api.music.nbs;

/** Stable import failure; offset is the input byte offset, or -1 for cue/compilation errors. */
public final class NbsException extends IllegalArgumentException {
    public enum Code {
        UNSUPPORTED_VERSION, LIMIT_EXCEEDED, TRUNCATED, INVALID_FIELD, TRAILING_DATA,
        UNSUPPORTED_FEATURE, INVALID_CUE, UNMAPPED_INSTRUMENT, UNSAFE_PATH, PITCH_OUT_OF_RANGE, INEXACT_TIMING
    }

    private final Code code;
    private final int offset;
    private final String field;

    public NbsException(Code code, int offset, String field, String message) {
        super(message);
        this.code = java.util.Objects.requireNonNull(code, "code");
        this.field = java.util.Objects.requireNonNull(field, "field");
        this.offset = offset;
    }

    public Code code() { return code; }
    public int offset() { return offset; }
    public String field() { return field; }
}
