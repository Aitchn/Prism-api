package com.aitchn.prism.api.hit;

import java.util.Objects;

/** Policies are expressible independently of runtime support; consult HitService.supportedShieldPolicies(). */
public record ShieldPolicy(Kind kind, double remainingDamageFraction) {
    public enum Kind { NATIVE, FULL, PARTIAL, UNBLOCKABLE }
    public static final ShieldPolicy NATIVE = new ShieldPolicy(Kind.NATIVE, 1);
    public static final ShieldPolicy FULL = new ShieldPolicy(Kind.FULL, 0);
    public static final ShieldPolicy UNBLOCKABLE = new ShieldPolicy(Kind.UNBLOCKABLE, 1);
    public ShieldPolicy {
        Objects.requireNonNull(kind, "kind");
        if (!Double.isFinite(remainingDamageFraction) || remainingDamageFraction < 0 || remainingDamageFraction > 1
                || kind != Kind.PARTIAL && remainingDamageFraction != (kind == Kind.FULL ? 0 : 1)) {
            throw new IllegalArgumentException("Invalid remaining damage fraction");
        }
    }
    /** .4 means 40% remains before armor, if the runtime supports this policy. */
    public static ShieldPolicy partial(double remainingDamageFraction) {
        return new ShieldPolicy(Kind.PARTIAL, remainingDamageFraction);
    }
}
