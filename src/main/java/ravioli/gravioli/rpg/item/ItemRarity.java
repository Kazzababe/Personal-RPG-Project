package ravioli.gravioli.rpg.item;

import org.bukkit.ChatColor;

public enum ItemRarity {
    COMMON(ChatColor.GRAY, 2),
    UNCOMMON(ChatColor.WHITE, 3),
    RARE(ChatColor.YELLOW, 4),
    MYTHIC(ChatColor.BLUE, 5),
    LEGENDARY(ChatColor.GOLD, 6);

    private ChatColor color;
    private int stats;

    ItemRarity(ChatColor color, int stats) {
        this.color = color;
        this.stats = stats;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public int getMaxStats() {
        return this.stats;
    }
}
