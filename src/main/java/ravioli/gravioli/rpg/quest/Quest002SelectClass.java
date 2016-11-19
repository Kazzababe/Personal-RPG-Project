package ravioli.gravioli.rpg.quest;

import org.bukkit.event.EventHandler;
import ravioli.gravioli.rpg.dialogue.event.DialogueFinishEvent;
import ravioli.gravioli.rpg.event.ClassSelectEvent;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class Quest002SelectClass extends Quest {
    public Quest002SelectClass(RPGPlayer player) {
        super(player);

        this.addObjective(0, "Go talk to the class Master and select your class");
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public String getName() {
        return "Select Class";
    }

    @EventHandler
    public void onClassSelect(ClassSelectEvent event) {
        event.getPlayer().completeQuest(this);
    }
}
