package com.aitchn.prism.api.protection;

import org.jetbrains.annotations.Nullable;

public record ProtectionDecision(Result result, @Nullable String reasonKey) {
    public enum Result {
        PASS,
        ALLOW,
        DENY
    }

    public static ProtectionDecision pass() {
        return new ProtectionDecision(Result.PASS, null);
    }

    public static ProtectionDecision allow() {
        return new ProtectionDecision(Result.ALLOW, null);
    }

    public static ProtectionDecision deny(String reasonKey) {
        return new ProtectionDecision(Result.DENY, reasonKey);
    }

    public boolean denied() {
        return result == Result.DENY;
    }
}
