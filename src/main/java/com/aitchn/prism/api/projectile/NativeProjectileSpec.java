package com.aitchn.prism.api.projectile;

import com.aitchn.prism.api.hit.HitEffect;
import com.aitchn.prism.api.hit.HitIntent;
import com.aitchn.prism.api.hit.ShieldPolicy;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

/** Native arrow damage/type remain authoritative; this declares shield policy and post-hit effects. */
public record NativeProjectileSpec(ShieldPolicy shield, UUID source, HitIntent.Attribution attribution,
                                   double pressure, List<HitEffect> effects, Map<String, String> tags,
                                   Consumer<NativeProjectileImpact> observer) {
    public NativeProjectileSpec {
        Objects.requireNonNull(shield, "shield");
        Objects.requireNonNull(attribution, "attribution");
        Objects.requireNonNull(observer, "observer");
        effects = List.copyOf(effects);
        tags = Map.copyOf(tags);
        if (!Double.isFinite(pressure) || pressure <= 0 || pressure > 2 || effects.size() > 16
                || tags.size() > 32 || tags.entrySet().stream().anyMatch(entry -> entry.getKey().length() > 64 || entry.getValue().length() > 256)) {
            throw new IllegalArgumentException("Invalid native projectile specification");
        }
    }
}
