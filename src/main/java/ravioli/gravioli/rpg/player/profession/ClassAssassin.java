package ravioli.gravioli.rpg.player.profession;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import ravioli.gravioli.rpg.player.skill.SkillCreepingDeath;
import ravioli.gravioli.rpg.util.ItemBuilder;

public class ClassAssassin extends BaseClass {
    public ClassAssassin() {
        super(0, "Assassin", "Tons of damage");

        this.skills.add(new SkillCreepingDeath());
    }

    @Override
    public ItemBuilder getItemBuilder() {
        return new ItemBuilder(Material.IRON_SWORD)
                .setName("Assassin")
                .setLore(this.getDescription())
                .addFlags(ItemFlag.HIDE_ATTRIBUTES);
    }
}
