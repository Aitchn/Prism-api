package com.aitchn.prism.api.hit;

import java.util.UUID;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.pointer.Pointer;

/** Optional native DamageSource pointers, preserving UUID metadata without retaining cross-region actors. */
public final class HitDamageContext {
    private HitDamageContext() { }
    public static final Pointer<UUID> HIT_ID = Pointer.pointer(UUID.class, Key.key("prism:hit_id"));
    public static final Pointer<UUID> CAST_ID = Pointer.pointer(UUID.class, Key.key("prism:cast_id"));
    public static final Pointer<UUID> SOURCE_ID = Pointer.pointer(UUID.class, Key.key("prism:hit_source_id"));
}
