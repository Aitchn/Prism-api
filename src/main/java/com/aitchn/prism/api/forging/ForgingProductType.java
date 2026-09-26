package com.aitchn.prism.api.forging;

import com.aitchn.prism.api.PrismKey;
import java.util.Objects;

/**
 * Carrier facts for an assembled product. Content selects a type with {@code product} on its
 * {@code prism:assembled_equipment} behavior; a bare name such as {@code sword} means {@code prism:sword}.
 *
 * @param slot slot receiving the standard attributes
 * @param attributes which standard statistic-to-attribute mapping Prism applies
 * @param durable whether the product has logical durability, a native wear counter, tool and weapon components.
 *                A non-durable product never breaks and its parts need not contribute durability
 * @param ammunition whether the product is fired as ammunition; its hit and damage traits are skipped when a
 *                   forged weapon already supplies them for the projectile
 * @param enchantingReference native item whose enchanting rules apply, or {@code null} for none
 * @param toolFamily tool family reported to Prism tool capabilities. A {@code minecraft:<tool>s} item tag also
 *                   grants native {@code minecraft:mineable/<tool>} mining rules; use {@link #EQUIPMENT_FAMILY}
 *                   for products that are not mining tools
 */
public record ForgingProductType(PrismKey id, ForgingProductSlot slot, Attributes attributes, boolean durable,
                                 boolean ammunition, PrismKey enchantingReference, PrismKey toolFamily) {
    public static final PrismKey EQUIPMENT_FAMILY = PrismKey.parse("prism:equipment");

    public enum Attributes {
        /** armor, armor toughness and knockback resistance */
        ARMOR,
        /** attack damage, attack knockback and attack speed */
        WEAPON,
        /** only the shared weight and broken-state modifiers */
        NONE
    }

    public ForgingProductType {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(slot, "slot");
        Objects.requireNonNull(attributes, "attributes");
        Objects.requireNonNull(toolFamily, "toolFamily");
        if (miningTool(toolFamily) && (!durable || !toolFamily.value().endsWith("s") || toolFamily.value().length() < 2)) {
            throw new IllegalArgumentException("A native mining family must be a durable minecraft:<tool>s tag");
        }
    }

    public boolean miningTool() {
        return miningTool(toolFamily);
    }

    private static boolean miningTool(PrismKey family) {
        return family.namespace().equals("minecraft");
    }
}
