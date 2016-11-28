package ravioli.gravioli.rpg.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.item.armour.ArmourSlot;
import ravioli.gravioli.rpg.item.armour.ArmourType;
import ravioli.gravioli.rpg.player.attribute.Attribute;
import ravioli.gravioli.rpg.util.RandomCollection;

import java.util.Random;

public class ItemManager {
    public static ItemStack createPotion(String name, int healAmount, int cooldown) {
        CustomPotion item = new CustomPotion();
        item.setTitle(name);
        item.setHealAmount(healAmount);
        item.setCooldown(cooldown);

        return item.build();
    }

    public static ItemStack generateRandomArmour(ArmourType type, ArmourSlot slot, int level) {
        RandomCollection<ItemRarity> rarities = new RandomCollection();
        rarities.add(20, ItemRarity.COMMON);
        rarities.add(15, ItemRarity.UNCOMMON);
        rarities.add(10, ItemRarity.RARE);
        rarities.add(5, ItemRarity.MYTHIC);
        rarities.add(1, ItemRarity.LEGENDARY);

        ItemRarity rarity = rarities.next();

        CustomArmour armour = new CustomArmour(type, slot);
        armour.setRequiredLevel(level);
        armour.setItemLevel(level);
        armour.setRarity(rarity);
        armour.setTitle("Randomly Generated Piece of Gear");

        for (int i = 0; i < rarity.getMaxStats(); i++) {
            armour.addAttribute(new Attribute(Attribute.AttributeType.values()[new Random().nextInt(Attribute.AttributeType.values().length)], 10));
        }

        return armour.build();
    }
}
