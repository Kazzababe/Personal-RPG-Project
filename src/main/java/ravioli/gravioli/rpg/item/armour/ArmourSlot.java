package ravioli.gravioli.rpg.item.armour;

import org.bukkit.Material;

public enum ArmourSlot {
    HEAD("HELMET"),
    CHEST("CHESTPLATE"),
    LEGS("LEGGINGS"),
    FEET("BOOTS");

    private String commonName;

    ArmourSlot(String commonName) {
        this.commonName = commonName;
    }

    public String getCommonName() {
        return this.commonName;
    }

    public static ArmourSlot getFromType(Material type) {
        if (type.name().contains("_")) {
            String slotName = type.name().split("_")[1];
            for (ArmourSlot slot : values()) {
                if (slot.getCommonName().equals(slotName)) {
                    return slot;
                }
            }
        }
        return null;
    }
}
