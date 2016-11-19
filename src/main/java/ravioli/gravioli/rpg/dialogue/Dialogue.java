package ravioli.gravioli.rpg.dialogue;

import org.bukkit.Bukkit;
import ravioli.gravioli.rpg.dialogue.event.DialogueFinishEvent;
import ravioli.gravioli.rpg.dialogue.stage.DialogueStage;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.util.ArrayList;

public class Dialogue implements Cloneable {
    protected ArrayList<DialogueStage> stages = new ArrayList();
    private int stage = -1;

    protected String prefix = "";
    private long startTime;

    public void start(RPGPlayer player) {
        this.startTime = System.currentTimeMillis();
        this.next(player);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public int getStage() {
        return this.stage;
    }

    public boolean hasNext() {
        return this.stage < this.stages.size() - 1;
    }

    public void next(RPGPlayer player) {
        if (++this.stage < this.stages.size()) {
            for (int i = 0; i < 20; i++) {
                player.sendMessage("");
            }
            this.getCurrentStage().sendMessage(player);
        } else {
            this.end(player);
        }
    }

    public void end(RPGPlayer player) {
        player.stopDialogue();
        Bukkit.getServer().getPluginManager().callEvent(new DialogueFinishEvent(this, player));
    }

    public void skipTo(RPGPlayer player, int stage) {
        this.stage = stage - 1;
        this.next(player);
    }

    public DialogueStage getCurrentStage() {
        return this.stages.get(this.stage);
    }

    public DialogueStage getStage(int index) {
        return this.stages.get(index);
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void onChatClick(RPGPlayer player, int stage, int messageId) {
        if (this.stage == this.getStage()) {
            this.getCurrentStage().onChatClick(player, messageId);
        }
    }

    @Override
    public Dialogue clone() {
        try {
            Dialogue dialogue = (Dialogue) super.clone();
            for (DialogueStage stage : dialogue.stages) {
                stage.setParent(dialogue);
            }
            return dialogue;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
