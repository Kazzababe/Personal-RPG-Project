package ravioli.gravioli.rpg.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.player.profession.BaseClass;

public class ClassSelectEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private RPGPlayer player;
    private BaseClass baseClass;

    public ClassSelectEvent(BaseClass kit, RPGPlayer player) {
        this.player = player;
    }

    public BaseClass getSelectedClass() {
        return this.baseClass;
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
