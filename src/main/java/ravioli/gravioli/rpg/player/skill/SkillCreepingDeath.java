package ravioli.gravioli.rpg.player.skill;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.player.skill.effect.SkillEffect;
import ravioli.gravioli.rpg.player.skill.effect.SkillEffectType;
import ravioli.gravioli.rpg.util.ItemBuilder;

public class SkillCreepingDeath extends ActiveSkill {
    public SkillCreepingDeath() {
        super(0, "Creeping Death", "Go invisible for 5 seconds, and the next attack you make will deal 10% increased damage with a 25% increased crit chance.");
    }

    @Override
    public long getCooldown() {
        return 15000;
    }

    @Override
    public void use(RPGPlayer player) {
        player.addSkillEffect(new SkillEffect(SkillEffectType.CREEPING_DEATH, 20 * 5));
    }

    @Override
    public ItemBuilder getItemRepresentation() {
        return new ItemBuilder(Material.WEB)
                .setName(this.getName())
                .setLore(this.getDescription());
    }

    @Override
    public int getLevelRequirement() {
        return 0;
    }
}
