package ravioli.gravioli.rpg.player.attribute;

import org.bukkit.ChatColor;
import ravioli.gravioli.rpg.util.CommonUtil;

public class Attribute {
    private AttributeType type;
    private int amount;

    public Attribute(AttributeType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public AttributeType getType() {
        return this.type;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static boolean isAttributeLine(String line) {
        String testLine = ChatColor.stripColor(line);
        String[] words = testLine.split("\\s+");

        String amount = words[0];

        boolean isAttr = false;
        for (AttributeType type : AttributeType.values()) {
            if (words[1].equalsIgnoreCase(type.getCommonName())) {
                isAttr = true;
                break;
            }
        }

        return CommonUtil.isInteger(amount) && isAttr;
    }

    public static Attribute parse(String line) {
        String testLine = ChatColor.stripColor(line);
        String[] words = testLine.split("\\s+");

        String amount = words[0];

        boolean isAttr = false;
        for (AttributeType type : AttributeType.values()) {
            if (words[1].equalsIgnoreCase(type.getCommonName())) {
                return new Attribute(type, Integer.parseInt(amount));
            }
        }
        return null;
    }

    public enum AttributeType {
        HEALTH("max health");

        private String commonName;

        private AttributeType(String commonName) {
            this.commonName = commonName;
        }

        public String getCommonName() {
            return this.commonName;
        }
    }
}
