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

    public enum AttributeType {
        TENACITY("tenacity"),
        AGILITY("agility"),
        MIGHT("might"),
        INSIGHT("insight"), ;

        private String commonName;

        AttributeType(String commonName) {
            this.commonName = commonName;
        }

        public String getCommonName() {
            return this.commonName;
        }
    }
}
