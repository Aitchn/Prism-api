package com.aitchn.prism.api.protection;

import com.aitchn.prism.api.PrismKey;

public interface ProtectionProvider {
    PrismKey id();

    ProtectionDecision query(ProtectionQuery query);

    /** Legacy providers fail closed until they explicitly support entity harm. Runs on the target owner. */
    default ProtectionDecision queryEntityHarm(EntityHarmQuery query) {
        return ProtectionDecision.deny("protection.prism.entity-harm-unsupported");
    }
}
