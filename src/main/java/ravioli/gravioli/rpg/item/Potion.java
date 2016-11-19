package ravioli.gravioli.rpg.item;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ravioli.gravioli.rpg.util.ItemBuilder;

import java.util.ArrayList;

public class Potion extends CustomItem {
    private int healAmount;
    private int cooldown;

    public Potion(ItemBuilder itemBuilder, int healAmount, int cooldown) {
        super(itemBuilder);
        this.healAmount = healAmount;
        this.cooldown = cooldown;
    }

    public Potion(ItemStack itemStack) {
        super(itemStack);

        ArrayList<String> lore = new ArrayList(this.itemBuilder.getLore());
        for (String line : lore) {
            line = ChatColor.stripColor(line);
            System.out.println(line);
            if (line.endsWith(" health instantly restored upon use")) {
                this.healAmount = Integer.parseInt(line.split("\\s+")[0]);
                this.itemBuilder.removeLore(line);
            } else if (line.endsWith("s cooldown")) {
                this.cooldown = Integer.parseInt(line.substring(0, line.indexOf("s ")));
                this.itemBuilder.removeLore(line);
            }
        }
    }

    public int getHealAmount() {
        return this.healAmount;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    @Override
    public ItemStack build() {
        ItemBuilder itemBuilder = ItemBuilder.parse(this.itemBuilder.build());

        itemBuilder.addLore(ChatColor.AQUA + "+" + this.healAmount + ChatColor.WHITE + " health instantly restored upon use");
        itemBuilder.addLore(ChatColor.WHITE + "" + this.cooldown + "s cooldown");
        return itemBuilder.build();
    }

    public static boolean isPotion(ItemStack item) {
        if (item == null) {
            return false;
        }
        ItemMeta itemMeta = item.getItemMeta();
        for (String line : itemMeta.getLore()) {
            if (line.endsWith(" health instantly restored upon use")) {
                return true;
            }
        }
        return false;
    }
}
