package ravioli.gravioli.rpg.dialogue.stage;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.player.RPGPlayer;

public abstract class DialogueStage {
    protected Dialogue parent;
    protected String message;

    public DialogueStage(Dialogue parent, String message) {
        this.parent = parent;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setParent(Dialogue parent) {
        this.parent = parent;
    }

    public void onChatClick(RPGPlayer player, int messageId) {}

    public abstract void sendMessage(RPGPlayer player);

    public static TextComponent getDialogueEnd(DialogueStage stage) {
        TextComponent message = new TextComponent("[");

        TextComponent next = new TextComponent("CONTINUE");
        next.setColor(ChatColor.DARK_GREEN);
        next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                "/dialogue next " +
                       stage.parent.getStage() + " 0 " + stage.parent.getStartTime()
        ));

        TextComponent end = new TextComponent("END");
        end.setColor(ChatColor.DARK_RED);
        end.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dialogue end"));

        message.addExtra(next);
        message.addExtra(" : ");
        message.addExtra(end);
        message.addExtra("]");

        return message;
    }
}
