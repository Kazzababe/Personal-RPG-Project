package ravioli.gravioli.rpg.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.util.ItemBuilder;

public class Container implements Listener {
    private ItemStack[] items;
    private ContainerAction[] actions;

    private String title;
    private int size;

    public Container(String title, int size) {
        this.title = title;
        this.size = (int) Math.ceil(size / 9.0) * 9;

        this.actions = new ContainerAction[this.size];
        this.items = new ItemStack[this.size];

        RPG.getPlugin().registerListener(this);
    }

    public Container setItem(int slot, ItemBuilder itemBuilder) {
        this.setItem(slot, itemBuilder, null);
        return this;
    }

    public Container setItem(int slot, ItemBuilder itemBuilder, ContainerAction action) {
        this.items[slot] = itemBuilder.build();
        this.actions[slot] = action;
        return this;
    }

    public void destroy() {
        HandlerList.unregisterAll(this);
        this.actions = null;
        this.items = null;
    }

    public void open(RPGPlayer player) {
        Inventory inventory = Bukkit.createInventory(null, this.size, this.title);
        for (int i = 0; i < this.size; i++) {
            ItemStack item = this.items[i];
            if (item != null) {
                inventory.setItem(i, item);
            }
        }
        player.getPlayer().openInventory(inventory);
    }

    public void close(RPGPlayer player) {
        player.getPlayer().closeInventory();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getTitle().equals(this.title)) {
            if (event.getSlot() == event.getRawSlot()) {
                event.setCancelled(true);
                if (this.actions[event.getSlot()] != null) {
                    this.actions[event.getSlot()].onClick(RPG.getPlayer(event.getWhoClicked().getUniqueId()), this, this.items[event.getSlot()]);
                }
            }
        }
    }
}
