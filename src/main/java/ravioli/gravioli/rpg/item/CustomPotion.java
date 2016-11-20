package ravioli.gravioli.rpg.item;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import ravioli.gravioli.rpg.util.ItemBuilder;

public class CustomPotion extends CustomItem {
    private int healAmount;
    private int cooldown;

    public CustomPotion() {
        super(Material.POTION, 1, (byte) 0);
        this.setItemType(CustomItemType.POTION);
    }

    public CustomPotion setHealAmount(int healAmount) {
        this.healAmount = healAmount;
        return this;
    }

    public CustomPotion setCooldown(int cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    public int getHealAmount() {
        return this.healAmount;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    @Override
    public ItemStack build() {
        ItemBuilder itemBuilder = new ItemBuilder(this.material, this.amount, this.data)
                .setName(this.title)
                .setLore(ChatColor.WHITE + "Instantly restores " + ChatColor.DARK_AQUA + this.healAmount + ChatColor.WHITE + " health.",
                         ChatColor.DARK_AQUA + "" + this.cooldown + ChatColor.WHITE + "s cooldown")
                .addFlags(ItemFlag.HIDE_POTION_EFFECTS)
                .setPotionType(PotionType.INSTANT_HEAL);

        net.minecraft.server.v1_10_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemBuilder.build());
        NBTTagCompound tag = nmsItemStack.getTag() == null? new NBTTagCompound() : nmsItemStack.getTag();
        tag.setBoolean("custom", true);
        tag.setString("type", this.type.getTypeString());
        tag.setInt("healAmount", this.healAmount);
        tag.setInt("cooldown", this.cooldown);

        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static CustomPotion parse(ItemStack itemStack) {
        CustomItem item = CustomItem.parse(itemStack);
        if (item != null) {
            NBTTagCompound tag = CraftItemStack.asNMSCopy(itemStack).getTag();
            if (tag.hasKey("healAmount") && tag.hasKey("cooldown")) {
                CustomPotion potion = new CustomPotion();
                potion.setHealAmount(tag.getInt("healAmount"));
                potion.setCooldown(tag.getInt("cooldown"));
                return potion;
            }
        }
        return null;
    }
}
