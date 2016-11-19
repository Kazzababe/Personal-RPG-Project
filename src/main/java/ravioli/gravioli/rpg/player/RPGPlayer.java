package ravioli.gravioli.rpg.player;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.dialogue.Dialogue;
import ravioli.gravioli.rpg.item.CustomItem;
import ravioli.gravioli.rpg.item.Potion;
import ravioli.gravioli.rpg.player.profession.BaseClass;
import ravioli.gravioli.rpg.player.skill.effect.SkillEffect;
import ravioli.gravioli.rpg.player.skill.effect.SkillEffectType;
import ravioli.gravioli.rpg.player.util.BaseStats;
import ravioli.gravioli.rpg.player.util.DamageSource;
import ravioli.gravioli.rpg.quest.Quest;
import ravioli.gravioli.rpg.util.CommonUtil;
import ravioli.gravioli.rpg.util.InventoryUtil;
import ravioli.gravioli.rpg.util.TitleItem;
import ravioli.gravioli.rpg.world.instance.Instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class RPGPlayer {
    private UUID uniqueId;

    public BaseClass playerClass;

    private double maxHealth = 100;
    private double health = maxHealth;

    private Experience experience;
    private int level;

    private long potionCooldown;
    private BankAccount bankAccount;

    private Dialogue dialogue;

    private ArrayList<TitleItem> titleQueue = new ArrayList();
    private boolean displayingTitle = false;

    private HashMap<String, Object> data = new HashMap();

    /**
     * A list of quests that the player has started
     */
    private HashMap<Integer, Quest> activeQuests = new HashMap();

    /**
     * A list of all the id's of quests that the player has finished
     */
    private HashSet<Integer> completedQuests = new HashSet();

    private HashSet<SkillEffect> skillEffects = new HashSet();

    private Instance instance;

    public RPGPlayer(UUID uniqueId) {
        this.uniqueId = uniqueId;

        this.level = 1;
        this.experience = new Experience(this);
        this.bankAccount = new BankAccount(this);

        this.updateInventory();
        this.getPlayer().getInventory().setHeldItemSlot(0);
        this.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public boolean addItem(CustomItem item) {
        return this.getPlayer().getInventory().addItem(item.build()).isEmpty();
    }

    public void removeItem(CustomItem item) {
        this.getPlayer().getInventory().removeItem(item.build());
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public Instance getInstance() {
        return this.instance;
    }

    public void startQuest(Quest quest) {
        this.activeQuests.put(quest.getId(), quest);
    }

    public void completeQuest(Quest quest) {
        this.activeQuests.remove(quest);
        this.completedQuests.add(quest.getId());
    }

    public boolean hasCompletedQuest(Integer id) {
        return this.completedQuests.contains(id);
    }

    public boolean isQuestActive(Integer id) {
        return this.activeQuests.containsKey(id);
    }

    public void addSkillEffect(SkillEffect effect) {
        this.skillEffects.add(effect);
        effect.start(this);
    }

    public boolean hasSkillEffect(SkillEffectType type) {
        for (SkillEffect effect : this.skillEffects) {
            if (effect.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public void removeSkillEffect(SkillEffectType type) {
        for (SkillEffect effect : this.skillEffects) {
            if (effect.getType().equals(type)) {
                this.skillEffects.remove(effect);
                effect.end(this);
                break;
            }
        }
    }

    private void setDefaultStats() {
        this.bankAccount.setPocket(BaseStats.CURRENCY);
    }

    public void setPlayerClass(BaseClass baseClass) {
        this.playerClass = baseClass;
        this.getPlayer().getInventory().setItem(5, baseClass.getSkills().get(0).getItemRepresentation().build());
    }

    public BaseClass getPlayerClass() {
        return this.playerClass;
    }

    private void updateInventory() {
        PlayerInventory inventory = this.getPlayer().getInventory();

        if (inventory.getItem(4) == null) {
            inventory.setItem(4, InventoryUtil.DEFAULT_POTION.build());
        }
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uniqueId);
    }

    public World getWorld() {
        return this.getPlayer().getWorld();
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void sendMessage(String message) {
        this.getPlayer().sendMessage(message);
    }

    public void usePotion() {
        if (this.potionCooldown < System.currentTimeMillis()) {
            this.potionCooldown = System.currentTimeMillis() + 1000 * InventoryUtil.getPotionCooldown(this);

            if (Potion.isPotion(this.getPotion())) {
                Potion potion = new Potion(this.getPotion());
                this.setHealth(this.getHealth() + potion.getHealAmount());
            }
        } else {
            this.sendMessage("POOTION COOLDOWN");
        }
    }

    protected void onLevelUp() {
        this.getWorld().playSound(this.getPlayer().getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);
        if (!this.isDialogueOpen()) {
            this.sendMessage("Congratulations, you have reached level " + this.level + "!");
        }
    }

    public Dialogue getDialogue() {
        return this.dialogue;
    }

    public void startDialogue(Dialogue dialogue) {
        this.dialogue = dialogue.clone();
        this.dialogue.start(this);
    }

    public void stopDialogue() {
        if (this.dialogue != null) {
            this.dialogue = null;
            this.sendMessage("Dialogue over");
        }
    }

    public boolean isDialogueOpen() {
        return this.dialogue != null;
    }

    public void sendTitleMessage(String title, String subTitle) {
        final TitleItem titleItem = new TitleItem(title, subTitle);
        if (!this.displayingTitle) {
            titleItem.send(this);
            this.displayingTitle = true;
            this.updateTitleQueue();
        } else {
            this.titleQueue.add(titleItem);
        }
    }
    private void updateTitleQueue() {
        Bukkit.getScheduler().runTaskLater(RPG.getPlugin(), () -> {
            if (!this.titleQueue.isEmpty()) {
                this.titleQueue.get(0).send(this);
                this.titleQueue.remove(0);
                this.updateTitleQueue();
            } else {
                this.displayingTitle = false;
            }
        }, 55L);
    }

    public ItemStack getPotion() {
        return this.getPlayer().getInventory().getItem(4);
    }

    public void hurt(DamageSource damageSource, int damage) {
        this.setHealth(this.getHealth() - damage);

        if (!this.isAlive()) {
            this.die(damageSource);
        }
    }

    public void die(DamageSource damageSource) {
        this.getPlayer().setHealth(0);
    }

    public void addExperience(long experience) {
        this.experience.addExperience(experience);
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(double health) {
        this.health = Math.min(health, this.maxHealth);
        this.getPlayer().setHealth(Math.round(Math.max((this.health / this.maxHealth) * 20.0, 1)));
        // Set action bar to properly display hp
    }

    public double getHealth() {
        return this.health;
    }

    public double getMaxHealth() {
        return this.maxHealth;
    }
}
