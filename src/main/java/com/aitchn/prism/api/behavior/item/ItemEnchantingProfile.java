package com.aitchn.prism.api.behavior.item;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/** The native item is used only for enchantment selection and book applicability. */
public record ItemEnchantingProfile(PrismKey nativeItem) {
    public ItemEnchantingProfile {
        Objects.requireNonNull(nativeItem, "nativeItem");
        if (!nativeItem.namespace().equals("minecraft")) {
            throw new IllegalArgumentException("Enchantment applicability requires a Minecraft item ID");
        }
    }
}
