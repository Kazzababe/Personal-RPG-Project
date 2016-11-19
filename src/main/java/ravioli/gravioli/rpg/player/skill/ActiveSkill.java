package ravioli.gravioli.rpg.player.skill;

import ravioli.gravioli.rpg.player.RPGPlayer;

import java.util.HashMap;

public abstract class ActiveSkill extends BaseSkill {
    private HashMap<RPGPlayer, Long> cooldown = new HashMap();

    public ActiveSkill(int id, String name, String description) {
        super(id, name, description);
    }

    protected void startCooldown(RPGPlayer player) {
        this.cooldown.put(player, System.currentTimeMillis());
    }

    protected long getCooldown(RPGPlayer player) {
        long cooldown = this.cooldown.get(player);
        long remaining = Math.max(0, this.getCooldown() - (System.currentTimeMillis() - cooldown));
        if (remaining <= 0) {
            this.cooldown.remove(player);
        }
        return remaining;
    }

    protected boolean isOnCooldown(RPGPlayer player) {
        return this.getCooldown() > 0;
    }

    public abstract long getCooldown();
    public abstract void use(RPGPlayer player);
}
