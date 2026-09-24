package com.aitchn.prism.api.behavior;

import java.util.Objects;

public record BehaviorResult(
        BehaviorOutcome outcome,
        NativeActionPolicy nativeAction,
        BehaviorPropagation propagation
) {
    private static final BehaviorResult PASS = new BehaviorResult(
            BehaviorOutcome.PASS, NativeActionPolicy.UNCHANGED, BehaviorPropagation.CONTINUE
    );

    public BehaviorResult {
        Objects.requireNonNull(outcome, "outcome");
        Objects.requireNonNull(nativeAction, "nativeAction");
        Objects.requireNonNull(propagation, "propagation");
    }

    public static BehaviorResult pass() {
        return PASS;
    }

    public static BehaviorResult continueSuccess() {
        return new BehaviorResult(
                BehaviorOutcome.SUCCESS, NativeActionPolicy.UNCHANGED, BehaviorPropagation.CONTINUE
        );
    }

    public static BehaviorResult consume() {
        return new BehaviorResult(BehaviorOutcome.SUCCESS, NativeActionPolicy.DENY, BehaviorPropagation.STOP);
    }

    public static BehaviorResult deny() {
        return new BehaviorResult(BehaviorOutcome.FAILURE, NativeActionPolicy.DENY, BehaviorPropagation.STOP);
    }

    public BehaviorResult merge(BehaviorResult next) {
        Objects.requireNonNull(next, "next");
        BehaviorOutcome mergedOutcome = next.outcome == BehaviorOutcome.PASS ? outcome : next.outcome;
        NativeActionPolicy mergedNative = nativeAction == NativeActionPolicy.DENY
                || next.nativeAction == NativeActionPolicy.DENY
                ? NativeActionPolicy.DENY
                : next.nativeAction == NativeActionPolicy.ALLOW
                ? NativeActionPolicy.ALLOW
                : nativeAction;
        BehaviorPropagation mergedPropagation = propagation == BehaviorPropagation.STOP
                || next.propagation == BehaviorPropagation.STOP
                ? BehaviorPropagation.STOP : BehaviorPropagation.CONTINUE;
        return new BehaviorResult(mergedOutcome, mergedNative, mergedPropagation);
    }
}
