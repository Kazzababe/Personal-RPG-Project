package ravioli.gravioli.rpg.item.armour;

import org.bukkit.Material;

public enum ArmourType {
    LEATHER("LEATHER"),
    GOLDEN("GOLD"),
    CHAINMAIL("CHAINMAIL"),
    IRON("IRON"),
    DIAMOND("DIAMOND");

    private String commonName;

    ArmourType(String commonName) {
        this.commonName = commonName;
    }

    public String getCommonName() {
        return this.commonName;
    }

    public static ArmourType getFromType(Material material) {
        if (material.name().contains("_")) {
            String typeName = material.name().split("_")[0];
            for (ArmourType type : values()) {
                if (type.getCommonName().equals(typeName)) {
                    return type;
                }
            }
        }
        return null;
    }
}
