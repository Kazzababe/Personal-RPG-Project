package ravioli.gravioli.rpg.item;

import java.util.HashMap;

public enum CustomItemType {
    POTION("custom.potion"),
    ARMOUR("custom.armour"),
    SWORD("custom.sword"),
    AXE("custom.axe"),
    DAGGER("custom.dagger"),
    BOW("custom.bow"),
    WAND("custom.wand"),
    STAFF("custom.staff");

    private String type;

    private CustomItemType(String type) {
        this.type = type;
    }

    public String getTypeString() {
        return this.type;
    }

    public static CustomItemType fromValue(String typeString) {
        for (CustomItemType type : values()) {
            if (type.getTypeString().equals(typeString)) {
                return type;
            }
        }
        return null;
    }
}
