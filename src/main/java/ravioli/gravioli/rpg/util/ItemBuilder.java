package ravioli.gravioli.rpg.util;

import lombok.Getter;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Getter
public class ItemBuilder {
    private Material material;

    private int amount;

    private byte data;

    private String name;

    private ArrayList<String> lore = new ArrayList();

    private HashSet<ItemFlag> flags = new HashSet();

    private PotionType potionType = PotionType.AWKWARD;

    public ItemBuilder(Material material) {
        this(material, 1, (byte) 0);
    }

    public ItemBuilder(Material material, int amount) {
        this(material, amount, (byte) 0);
    }

    public ItemBuilder(Material material, int amount, byte data) {
        this.material = material;
        this.amount = amount;
        this.data = data;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setLore(String... lines) {
        this.lore.clear();
        this.addLore(lines);
        return this;
    }

    public ItemBuilder addLore(String... lines) {
        for (String line : lines) {
            String wrappedLine = CommonUtil.wrapString(line, '&', 50, false);
            String[] wrappedLines = wrappedLine.split("\\r??\\n");
            for (String line2 : wrappedLines) {
                this.lore.add(line2);
            }
        }
        return this;
    }

    public ItemBuilder removeLore(String... lines) {
        this.lore.removeAll(Arrays.asList(lines));
        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        this.flags.addAll(Arrays.asList(flags));
        return this;
    }

    public ItemBuilder setPotionType(PotionType type) {
        this.potionType = type;
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(this.material, this.amount, this.data);

        ItemMeta itemMeta = item.getItemMeta();
        if (this.name != null) {
            itemMeta.setDisplayName(this.name);
        }
        if (!this.lore.isEmpty()) {
            itemMeta.setLore(this.lore);
        }
        if (!this.flags.isEmpty()) {
            itemMeta.addItemFlags(this.flags.toArray(new ItemFlag[this.flags.size()]));
        }
        if (itemMeta instanceof PotionMeta) {
            PotionMeta potionMeta = (PotionMeta) itemMeta;
            potionMeta.setBasePotionData(new PotionData(this.potionType));
        }
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemBuilder parse(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();

        ItemBuilder itemBuilder = new ItemBuilder(item.getType(), item.getAmount(), (byte) item.getDurability());
        if (itemMeta.getDisplayName() != null) {
            itemBuilder.setName(itemMeta.getDisplayName());
        }
        if (itemMeta.getLore() != null && !itemMeta.getLore().isEmpty()) {
            itemBuilder.setLore(itemMeta.getLore().toArray(new String[itemMeta.getLore().size()]));
        }
        if (itemMeta instanceof PotionMeta) {
            itemBuilder.setPotionType(((PotionMeta) itemMeta).getBasePotionData().getType());
        }
        if (!itemMeta.getItemFlags().isEmpty()) {
            itemMeta.getItemFlags().forEach(flag -> itemBuilder.addFlags(flag));
        }
        return itemBuilder;
    }
}
