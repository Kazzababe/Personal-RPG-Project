package ravioli.gravioli.rpg.gui;

import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.player.RPGPlayer;

public interface ContainerAction {
    public void onClick(RPGPlayer player, Container container, ItemStack item);
}
