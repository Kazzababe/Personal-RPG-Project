package ravioli.gravioli.rpg.player.profession;

import ravioli.gravioli.rpg.player.skill.BaseSkill;
import ravioli.gravioli.rpg.util.ItemBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseClass {
    private int id;
    private String name;
    private String description;

    public static final HashMap<Integer, BaseClass> classes = new HashMap();
    public static final BaseClass ASSASSIN = new ClassAssassin();

    protected ArrayList<BaseSkill> skills = new ArrayList();

    public BaseClass(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;

        this.classes.put(id, this);
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

    public ArrayList<BaseSkill> getSkills() {
        return this.skills;
    }

    public abstract ItemBuilder getItemBuilder();
}
