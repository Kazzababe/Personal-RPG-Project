package ravioli.gravioli.rpg.dialogue.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class DialogueFinishEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Dialogue dialogue;
    private RPGPlayer player;

    public DialogueFinishEvent(Dialogue dialogue, RPGPlayer player) {
        this.dialogue = dialogue;
        this.player = player;
    }

    public Dialogue getDialogue() {
        return this.dialogue;
    }

    public RPGPlayer getPlayer() {
        return this.player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
