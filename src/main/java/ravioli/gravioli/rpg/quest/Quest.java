package ravioli.gravioli.rpg.quest;

import org.bukkit.event.Listener;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.util.HashMap;

public abstract class Quest implements Listener {
    private RPGPlayer owningPlayer;

    /**
     * A list of strings that is used to display an objective to the player, corresponds to stage
     */
    private HashMap<Integer, String> objectives = new HashMap();

    /**
     * Custom data to be stored in the database. Used to track specific details for the player/quest
     */
    private HashMap<String, Object> data = new HashMap<String, Object>();

    private int stage;

    public Quest(RPGPlayer player) {
        this.owningPlayer = player;

        if (player != null) {
            RPG.getPlugin().registerListener(this);
        }
    }

    public abstract int getId();
    public abstract String getName();

    protected void addObjective(int index, String objective) {
        this.objectives.put(index, objective);
    }

    protected String getObjective(int index) {
        return this.objectives.get(index);
    }

    public void setData(String key, Object value) {
        this.data.put(key, value);
    }

    public Object getData(String key) {
        return this.data.get(key);
    }

    public RPGPlayer getOwningPlayer() {
        return this.owningPlayer;
    }

    public int getStage() {
        return this.stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
        this.getOwningPlayer().sendMessage(this.getObjective(stage));
    }
}