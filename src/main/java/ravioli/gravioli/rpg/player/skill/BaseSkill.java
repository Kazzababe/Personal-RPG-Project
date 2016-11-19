package ravioli.gravioli.rpg.player.skill;

import org.bukkit.inventory.ItemStack;
import ravioli.gravioli.rpg.util.ItemBuilder;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseSkill implements Cloneable {
    enum SkillType {
        PASSIVE,
        ACTIVE,
        HYBRID
    }

    private static final HashMap<ItemStack, BaseSkill> skills = new HashMap();
    public static final BaseSkill CREEPING_DEATH = new SkillCreepingDeath();

    private int id;
    private String name;
    private String description;

    public BaseSkill(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;

        skills.put(this.getItemRepresentation().build(), this);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public static BaseSkill getSkill(ItemStack item) {
        for (Map.Entry<ItemStack, BaseSkill> entry : skills.entrySet()) {
            if (entry.getKey().isSimilar(item)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static boolean isSkill(ItemStack item) {
        return getSkill(item) != null;
    }

    public abstract ItemBuilder getItemRepresentation();
    public abstract int getLevelRequirement();

    @Override
    public BaseSkill clone() {
        try {
            return (BaseSkill) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
