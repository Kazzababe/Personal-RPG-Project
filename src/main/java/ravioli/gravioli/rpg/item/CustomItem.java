package ravioli.gravioli.rpg.item;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ravioli.gravioli.rpg.player.attribute.Attribute;
import ravioli.gravioli.rpg.util.ItemBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Slight duplicate of item builder at the moment, but I'll change it at a later date
 */
public class CustomItem {
    protected String title;
    protected ArrayList<String> lore = new ArrayList<String>();

    protected Material material;
    protected CustomItemType type;
    protected int amount;
    protected byte data;

    public CustomItem(Material type, int amount, byte data) {
        this.material = type;
        this.amount = amount;
        this.data = data;

        this.type = CustomItemType.GENERIC;
    }

    public CustomItem(Material type, byte data) {
        this(type, 0, data);
    }

    public CustomItem(Material type) {
        this(type, 0, (byte) 0);
    }

    public CustomItem setItemType(CustomItemType type) {
        this.type = type;
        return this;
    }

    public CustomItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomItem addLore(String... lines) {
        this.lore.addAll(Arrays.asList(lines));
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public ArrayList<String> getLore() {
        return this.lore;
    }

    public Material getMaterial() {
        return this.material;
    }

    public int getAmount() {
        return this.amount;
    }

    public byte getData() {
        return this.data;
    }

    public ItemStack build() {
        ItemBuilder itemBuilder = new ItemBuilder(this.material, this.amount, this.data)
                .setName(this.title)
                .setLore(this.lore.toArray(new String[this.lore.size()]));

        net.minecraft.server.v1_10_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemBuilder.build());
        NBTTagCompound tag = nmsItemStack.getTag() == null? new NBTTagCompound() : nmsItemStack.getTag();
        tag.setBoolean("custom", true);
        tag.setString("type", this.type.getTypeString());

        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static boolean isCustomItem(ItemStack item) {
        return isCustomItem(item, null);
    }

    public static boolean isCustomItem(ItemStack item, CustomItemType type) {
        if (item == null) {
            return false;
        }

        net.minecraft.server.v1_10_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);
        if (nmsItemStack.hasTag()) {
            NBTTagCompound tag = nmsItemStack.getTag();
            return (tag.hasKey("custom") && tag.getBoolean("custom")) && (type == null? true : (tag.hasKey("type") && tag.getString("type").equals(type.getTypeString())));
        }
        return false;
    }

    public static CustomItem parse(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }

        CustomItem item = new CustomItem(itemStack.getType(), itemStack.getAmount(), (byte) itemStack.getDurability());

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta.hasDisplayName()) {
            item.setTitle(itemMeta.getDisplayName());
        }
        if (itemMeta.hasLore()) {
            item.addLore(itemMeta.getLore().toArray(new String[itemMeta.getLore().size()]));
        }

        net.minecraft.server.v1_10_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        if (nmsItemStack.hasTag()) {
            NBTTagCompound tag = nmsItemStack.getTag();
            if (tag.hasKey("custom") && tag.getBoolean("custom")) {
                if (tag.hasKey("type")) {
                    item.setItemType(CustomItemType.fromValue(tag.getString("type")));
                    return item;
                }
            }
        }
        return null;
    }
}
