package ravioli.gravioli.rpg.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemManager {
    public static ItemStack createPotion(String name, int healAmount, int cooldown) {
        CustomPotion item = new CustomPotion();
        item.setTitle(name);
        item.setHealAmount(healAmount);
        item.setCooldown(cooldown);

        return item.build();
    }
}
