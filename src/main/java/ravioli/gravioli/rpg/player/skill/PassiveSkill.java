package ravioli.gravioli.rpg.player.skill;

import ravioli.gravioli.rpg.player.RPGPlayer;

public abstract class PassiveSkill extends BaseSkill {
    public PassiveSkill(int id, String name, String description) {
        super(id, name, description);
    }

    public abstract void buffer(RPGPlayer player);
}
