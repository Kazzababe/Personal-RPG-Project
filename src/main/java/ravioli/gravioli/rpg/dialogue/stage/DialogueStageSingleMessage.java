package ravioli.gravioli.rpg.dialogue.stage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.dialogue.DialogueAction;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class DialogueStageSingleMessage extends DialogueStage {
    private DialogueAction action;

    public DialogueStageSingleMessage(Dialogue parent, String message, DialogueAction action) {
        super(parent, message);
        this.action = action;
    }

    public DialogueStageSingleMessage(Dialogue parent, String message) {
        super(parent, message);
    }

    @Override
    public void sendMessage(RPGPlayer player) {
        player.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "[" + this.parent.getPrefix() + "]");
        player.sendMessage(this.message);

        if (this.parent.hasNext()) {
            player.sendMessage("");

            TextComponent message = DialogueStage.getDialogueEnd(this);
            player.getPlayer().spigot().sendMessage(message);
        } else {
            this.parent.end(player);
        }
        if (this.action != null) {
            this.action.run(player, this.parent);
        }
    }

    @Override
    public void onChatClick(RPGPlayer player, int messageId) {
        this.parent.next(player);
    }
}
