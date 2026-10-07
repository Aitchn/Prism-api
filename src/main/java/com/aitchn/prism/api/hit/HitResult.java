package com.aitchn.prism.api.hit;

import java.util.Objects;

/** Event final damage is native units; observed health/absorption loss and blocked amount are logical units. */
public record HitResult(Status status, Attribution attribution, boolean nativeAccepted, double eventFinalDamage,
                        double healthLoss, double absorptionLoss, double blockedDamage, int effectsCommitted,
                        String detail) {
    public enum Status { DAMAGE, BLOCKED, NO_DAMAGE, CONTACT, CANCELLED, PROTECTION_DENIED, DUPLICATE,
        INVALID_TARGET, STALE, OWNER_DISABLED, CLOSED, TIMEOUT, BUDGET_EXCEEDED, UNSUPPORTED_SHIELD,
        UNSUPPORTED_ATTRIBUTION, UNSUPPORTED_EFFECT_GATE, UNKNOWN_DAMAGE_TYPE, NO_NATIVE_EVENT, AMBIGUOUS_NATIVE_EVENT,
        NATIVE_COMMIT_REJECTED, NATIVE_PARTIAL_FAILURE, EVENT_FAILED, FAILED, EFFECT_FAILED }
    public enum Attribution { NONE, NATIVE, UUID_CONTEXT_ONLY }
    public HitResult {
        Objects.requireNonNull(status, "status"); Objects.requireNonNull(attribution, "attribution"); Objects.requireNonNull(detail, "detail");
        for (double value : new double[]{eventFinalDamage, healthLoss, absorptionLoss, blockedDamage}) {
            if (!Double.isFinite(value) || value < 0) throw new IllegalArgumentException("Invalid hit result amount");
        }
        if (effectsCommitted < 0 || effectsCommitted > 16) throw new IllegalArgumentException("Invalid effect count");
    }
    public boolean contactAccepted() { return nativeAccepted || status == Status.CONTACT || status == Status.EFFECT_FAILED; }
    public static HitResult refused(Status status, String detail) { return new HitResult(status, Attribution.NONE, false, 0, 0, 0, 0, 0, detail); }
}
