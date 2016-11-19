package ravioli.gravioli.rpg.dialogue.stage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.util.ArrayList;

public class DialogueStageMultipleChoice extends DialogueStage {
    private ArrayList<DialogueOption> options = new ArrayList();

    public DialogueStageMultipleChoice(Dialogue parent, String message) {
        super(parent, message);
    }

    public ArrayList<DialogueOption> getOptions() {
        return this.options;
    }

    @Override
    public void sendMessage(RPGPlayer player) {
        player.sendMessage(ChatColor.BOLD + "" + ChatColor.GOLD + "[" + this.parent.getPrefix() + "]");
        player.sendMessage(this.message);
        for (int i = 0; i < this.options.size(); i++) {
            TextComponent option = new TextComponent(ChatColor.GRAY + "> ");
            TextComponent next = new TextComponent(this.options.get(i).message);
            next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                    "/dialogue next " +
                    this.parent.getStage() + " " + i + " " + this.parent.getStartTime()
            ));
            option.addExtra(next);

            player.getPlayer().spigot().sendMessage(option);
        }
        player.sendMessage("");

        TextComponent message = new TextComponent("[");

        TextComponent end = new TextComponent("END");
        end.setColor(ChatColor.DARK_RED);
        end.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dialogue end"));

        message.addExtra(end);
        message.addExtra("]");

        player.getPlayer().spigot().sendMessage(message);
    }

    @Override
    public void onChatClick(RPGPlayer player, int messageId) {
        this.options.get(messageId).action.run(player, this.parent);
    }
}
