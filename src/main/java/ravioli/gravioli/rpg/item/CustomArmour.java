package ravioli.gravioli.rpg.item;

import net.minecraft.server.v1_10_R1.NBTBase;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagList;
import net.minecraft.server.v1_10_R1.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import ravioli.gravioli.rpg.item.armour.ArmourSlot;
import ravioli.gravioli.rpg.item.armour.ArmourType;
import ravioli.gravioli.rpg.player.attribute.Attribute;
import ravioli.gravioli.rpg.util.ItemBuilder;

import java.util.ArrayList;

public class CustomArmour extends CustomItem {
    private ArmourType armourType;
    private ArmourSlot slot;

    private int requiredLevel;
    private int itemLevel;

    private ArrayList<Attribute> attributes = new ArrayList();

    public CustomArmour(ArmourType type, ArmourSlot slot) {
        super(Material.valueOf(type.getCommonName() + "_" + slot.getCommonName()), 1, (byte) 0);
        this.setItemType(CustomItemType.ARMOUR);

        this.armourType = type;
        this.slot = slot;
    }

    public CustomArmour(ItemStack item) {
        super(item.getType());

        Material material = item.getType();
        this.slot = ArmourSlot.getFromType(material);
        this.armourType = ArmourType.getFromType(material);

        this.setItemType(CustomItemType.ARMOUR);
    }

    public ArmourType getArmourType() {
        return this.armourType;
    }

    public ArmourSlot getSlot() {
        return this.slot;
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public void setRequiredLevel(int level) {
        this.requiredLevel = level;
    }

    public void setItemLevel(int level) {
        this.itemLevel = level;
    }

    public boolean removeAttribute(Attribute.AttributeType type) {
        for (Attribute attribute : this.attributes) {
            if (attribute.getType() == type) {
                this.attributes.remove(attribute);
                return true;
            }
        }
        return false;
    }

    public Attribute getAttribute(Attribute.AttributeType type) {
        for (Attribute attribute : this.attributes) {
            if (attribute.getType() == type) {
                return attribute;
            }
        }
        return null;
    }

    public boolean hasAttribute(Attribute.AttributeType type) {
        return this.getAttribute(type) != null;
    }

    public ArrayList<Attribute> getAttributes() {
        return this.attributes;
    }

    public int getRequiredLevel() {
        return this.requiredLevel;
    }

    public int getItemLevel() {
        return this.itemLevel;
    }

    @Override
    public ItemStack build() {
        ItemBuilder itemBuilder = new ItemBuilder(this.material, this.amount, this.data)
                .setName(this.title)
                .addFlags(ItemFlag.HIDE_ATTRIBUTES)
                .addLore(ChatColor.YELLOW + "Item Level " + this.itemLevel, "");
        for (Attribute attribute : this.attributes) {
            itemBuilder.addLore(ChatColor.WHITE + (attribute.getAmount() >= 0? "+" : "-") + " " + ChatColor.BLUE + attribute.getAmount() + " " + attribute.getType().getCommonName());
        }
        itemBuilder.addLore(ChatColor.WHITE + "Required Level " + this.requiredLevel);

        net.minecraft.server.v1_10_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemBuilder.build());
        NBTTagCompound tag = nmsItemStack.getTag() == null? new NBTTagCompound() : nmsItemStack.getTag();
        tag.setBoolean("custom", true);
        tag.setString("type", this.type.getTypeString());

        NBTTagList list = new NBTTagList();
        for (Attribute attribute : this.attributes) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setString("type", attribute.getType().name());
            compound.setInt("amount", attribute.getAmount());
            list.add(compound);
        }
        tag.set("attributes", list);
        tag.setInt("requiredLevel", this.requiredLevel);
        tag.setInt("itemLevel", this.requiredLevel);

        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static CustomArmour parse(ItemStack itemStack) {
        CustomItem item = CustomItem.parse(itemStack);
        if (item != null) {
            NBTTagCompound tag = CraftItemStack.asNMSCopy(itemStack).getTag();
            if (tag.hasKey("attributes") && tag.hasKey("requiredLevel") && tag.hasKey("itemLevel")) {
                CustomArmour armour = new CustomArmour(itemStack);
                armour.setItemLevel(tag.getInt("itemLevel"));
                armour.setRequiredLevel(tag.getInt("requiredLevel"));

                NBTTagList listCompound = (NBTTagList) tag.get("attributes");
                for (int i = 0; i < listCompound.size(); i++) {
                    NBTTagCompound compound = listCompound.get(i);
                    Attribute attribute = new Attribute(Attribute.AttributeType.valueOf(compound.getString("type")), compound.getInt("amount"));
                    armour.addAttribute(attribute);
                }

                return armour;
            }
        }
        return null;
    }
}
