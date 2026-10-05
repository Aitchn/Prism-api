package com.aitchn.prism.api.client;

import java.util.concurrent.atomic.AtomicInteger;

/** Shared Prism/addon packet-only entity ID allocation. Never reuse IDs or shade the API.
 * Allocation creates no entities; the caller owns viewer packets and lifecycle cleanup. @since 3.25 */
public final class VirtualEntityIds {
    private static final AtomicInteger IDS = new AtomicInteger(-1);
    private VirtualEntityIds() { }
    public static int next() {
        while (true) {
            int id = IDS.get();
            if (id >= 0 || id == Integer.MIN_VALUE) throw new IllegalStateException("Virtual entity ID space exhausted");
            if (IDS.compareAndSet(id, id - 1)) return id;
        }
    }
}
