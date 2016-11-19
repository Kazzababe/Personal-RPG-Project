package ravioli.gravioli.rpg.util;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;
import ravioli.gravioli.rpg.item.Potion;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class InventoryUtil {
    public static final Potion DEFAULT_POTION = new Potion(
            new ItemBuilder(Material.POTION)
                    .setName(ChatColor.GOLD + "Default Potion")
                    .addFlags(ItemFlag.HIDE_POTION_EFFECTS)
                    .setPotionType(PotionType.INSTANT_HEAL),
            100,
            5);

    public static int getPotionCooldown(RPGPlayer player) {
        ItemStack potion = player.getPotion();
        if (Potion.isPotion(potion)) {
            return new Potion(potion).getCooldown();
        }
        return 60;
    }
}
