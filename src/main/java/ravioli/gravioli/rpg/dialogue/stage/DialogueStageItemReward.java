package ravioli.gravioli.rpg.dialogue.stage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.item.CustomItem;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.util.ItemBuilder;

import java.util.ArrayList;

public class DialogueStageItemReward extends DialogueStage {
    private ArrayList<CustomItem> items = new ArrayList();
    private boolean end;

    public DialogueStageItemReward(Dialogue parent, String message, boolean end) {
        super(parent, message);
        this.end = end;
    }

    public ArrayList<CustomItem> getItems() {
        return this.items;
    }

    @Override
    public void sendMessage(RPGPlayer player) {
        player.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "[" + this.parent.getPrefix() + "]");
        player.sendMessage(this.message);

        if (this.parent.hasNext() && !this.end) {
            player.sendMessage("");

            TextComponent message = DialogueStage.getDialogueEnd(this);
            player.getPlayer().spigot().sendMessage(message);
        } else {
            this.parent.end(player);
        }

        for (CustomItem item : this.items) {
            player.addItem(item);
            player.sendTitleMessage(ChatColor.GREEN + item.getItemBuilder().getName() + ChatColor.GREEN + " aqquired!", "\u2694");
        }
    }
}
