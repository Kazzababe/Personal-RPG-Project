package ravioli.gravioli.rpg.player.skill.effect;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class SkillEffectType {
    public static SkillEffectType CREEPING_DEATH = new SkillEffectType(new SkillAction() {
        @Override
        public void start(RPGPlayer player) {
            player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 5, 1));
        }

        @Override
        public void end(RPGPlayer player) {
            if (player.getPlayer().hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                player.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
            }
        }
    });

    private SkillAction actions;

    private SkillEffectType(SkillAction actions) {
        this.actions = actions;
    }

    public void start(RPGPlayer player) {
        this.actions.start(player);
    }

    public void end(RPGPlayer player) {
        this.actions.end(player);
    }

    private interface SkillAction {
        public void start(RPGPlayer player);
        public void end(RPGPlayer player);
    }
}
