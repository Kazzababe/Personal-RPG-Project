package ravioli.gravioli.rpg.item;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ravioli.gravioli.rpg.player.attribute.Attribute;
import ravioli.gravioli.rpg.util.ItemBuilder;

import java.util.ArrayList;
import java.util.Iterator;

public class CustomItem {
    protected ItemBuilder itemBuilder;
    private ArrayList<Attribute> attributes = new ArrayList();

    public CustomItem(ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public CustomItem(ItemStack itemStack) {
        this.itemBuilder = ItemBuilder.parse(itemStack);

        ArrayList<String> toRemove = new ArrayList();
        this.itemBuilder.getLore().stream().filter(Attribute::isAttributeLine).forEach(line -> {
            Attribute attr = Attribute.parse(line);
            if (attr != null) {
                this.addAttribute(attr);
                toRemove.add(line);
            }
        });
        this.itemBuilder.removeLore(toRemove.toArray(new String[toRemove.size()]));
    }

    public void addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
    }

    public boolean removeAttribute(Attribute.AttributeType type) {
        Iterator<Attribute> attributes = this.attributes.iterator();
        while (attributes.hasNext()) {
            Attribute attribute = attributes.next();
            if (attribute.getType() == type) {
                attributes.remove();
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

    public ArrayList<Attribute> getAttributes() {
        return this.attributes;
    }

    public ItemBuilder getItemBuilder() {
        return this.itemBuilder;
    }

    public ItemStack build() {
        ItemBuilder itemBuilder = ItemBuilder.parse(this.itemBuilder.build());

        for (Attribute attribute : this.attributes) {
            Attribute.AttributeType type = attribute.getType();
            int  amount = attribute.getAmount();

            String line = "";
            switch (type) {
                case HEALTH:
                    line = ChatColor.AQUA + (amount > 0? "+" : "") + amount + " " + ChatColor.WHITE + type.getCommonName();
                    break;
            }
            itemBuilder.addLore(line);
        }
        return itemBuilder.build();
    }
}
