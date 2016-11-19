package ravioli.gravioli.rpg.dialogue;

import ravioli.gravioli.rpg.player.RPGPlayer;

public interface DialogueAction {
    public void run(RPGPlayer player, Dialogue dialogue);
}
