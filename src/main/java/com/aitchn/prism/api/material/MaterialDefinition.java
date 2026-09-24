package com.aitchn.prism.api.material;

import com.aitchn.prism.api.PrismKey;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public record MaterialDefinition(
        PrismKey id,
        String displayNameKey,
        int harvestLevel,
        MaterialPowerBand powerBand,
        Map<EquipmentType, MaterialEquipmentDefinition> equipment,
        PrismKey repairItem
) {
    public MaterialDefinition {
        Objects.requireNonNull(id, "id");
        if (displayNameKey == null || displayNameKey.isBlank()) {
            throw new IllegalArgumentException("Material display name keys must be non-blank");
        }
        if (harvestLevel < 0) {
            throw new IllegalArgumentException("Material harvest levels cannot be negative");
        }
        Objects.requireNonNull(powerBand, "powerBand");
        equipment = Map.copyOf(Objects.requireNonNull(equipment, "equipment"));
        equipment.forEach((type, definition) -> {
            Objects.requireNonNull(type, "Material equipment type cannot be null");
            Objects.requireNonNull(definition, "Material equipment definition cannot be null");
            if (definition.type() != type) {
                throw new IllegalArgumentException("Material equipment map keys must match their definitions");
            }
        });
    }

    public MaterialDefinition(PrismKey id, String displayNameKey, int harvestLevel, MaterialPowerBand powerBand,
                              Map<EquipmentType, MaterialEquipmentDefinition> equipment) {
        this(id, displayNameKey, harvestLevel, powerBand, equipment, null);
    }

    public Optional<MaterialEquipmentDefinition> equipment(EquipmentType type) {
        return Optional.ofNullable(equipment.get(Objects.requireNonNull(type, "type")));
    }
}
