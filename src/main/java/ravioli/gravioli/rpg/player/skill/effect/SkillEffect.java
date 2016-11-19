package ravioli.gravioli.rpg.player.skill.effect;

import org.bukkit.Bukkit;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class SkillEffect {
    private SkillEffectType type;
    private int duration;
    private boolean instant;

    public SkillEffect(SkillEffectType type, int duration) {
        this(type, duration, false);
    }

    public SkillEffect(SkillEffectType type, int duration, boolean instant) {
        this.type = type;
        this.duration = duration;
        this.instant = instant;
    }

    public SkillEffectType getType() {
        return this.type;
    }

    public boolean isInstant() {
        return this.instant;
    }

    public void start(RPGPlayer player) {
        this.type.start(player);
        if (!this.instant) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(RPG.getPlugin(), () -> {
                player.removeSkillEffect(this.type);
            }, this.duration);
        }
    }

    public void end(RPGPlayer player) {
        this.type.end(player);
    }
}
