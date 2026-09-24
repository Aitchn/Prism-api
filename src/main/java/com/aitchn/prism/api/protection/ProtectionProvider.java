package com.aitchn.prism.api.protection;

import com.aitchn.prism.api.PrismKey;

public interface ProtectionProvider {
    PrismKey id();

    ProtectionDecision query(ProtectionQuery query);
}
