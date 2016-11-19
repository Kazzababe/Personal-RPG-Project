package ravioli.gravioli.rpg.listener;

import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.actor.BaseActor;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.player.skill.ActiveSkill;
import ravioli.gravioli.rpg.player.skill.BaseSkill;
import ravioli.gravioli.rpg.player.skill.effect.SkillEffectType;
import ravioli.gravioli.rpg.player.util.DamageSource;

import java.util.Iterator;

public class PlayerListeners implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        ItemStack item = event.getCursor();
        if (event.getSlotType() == InventoryType.SlotType.QUICKBAR && event.getSlot() > 5) {
            if (item != null && item.getType() != Material.AIR && !BaseSkill.isSkill(item)) {
                event.setCancelled(true);
                event.getWhoClicked().sendMessage(ChatColor.RED + "This slot is reserved for a skill");
            }
        }
    }

    @EventHandler
    public void onEntityKilled(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            RPGPlayer player = RPG.getPlayer(event.getEntity().getKiller());
            player.addExperience((long) event.getEntity().getMaxHealth());
        }
        event.setDroppedExp(0);
    }

    @EventHandler
    public void onEntityDamaged(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            RPGPlayer player = RPG.getPlayer((Player) event.getEntity());
            if (player != null) {
                player.hurt(new DamageSource(event.getEntity().getName(), event.getEntityType()), (int) Math.ceil(event.getDamage()));
                event.setDamage(0);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            RPGPlayer damager = RPG.getPlayer((Player) event.getDamager());
            if (damager.hasSkillEffect(SkillEffectType.CREEPING_DEATH)) {
                double damage = event.getDamage() * 1.1;
                if (Math.random() <= 0.25) {
                    damage *= 2;
                    damager.sendMessage("CRITICAL STRIKE");
                }
                event.setDamage(damage);

                damager.sendMessage("You dealt " + damage + " damage");
                damager.removeSkillEffect(SkillEffectType.CREEPING_DEATH);
            }
        }
    }

    @EventHandler
    public void onEntityHealthRegain(EntityRegainHealthEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        event.setAmount(0);
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        RPGPlayer player = RPG.getPlayer(event.getPlayer());

        if (player.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        if (event.getNewSlot() >= 4) {
            event.setCancelled(true);
        }

        if (event.getNewSlot() == 4) {
            player.usePotion();
        } else if (event.getNewSlot() > 4) {
            ItemStack item = player.getPlayer().getInventory().getItem(event.getNewSlot());
            if (item != null) {
                BaseSkill skill = BaseSkill.getSkill(item);
                if (skill != null) {
                    if (skill instanceof ActiveSkill) {
                        ((ActiveSkill) skill).use(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (RPG.getPlayer(event.getPlayer()).isDialogueOpen()) {
            event.setCancelled(true);
            return;
        }

        String format = "[<player>]: <message>";
        format = format.replace("<player>", event.getPlayer().getName());
        format = format.replace("<message>", "%2$s");

        event.setFormat(format);

        Iterator<Player> iterator = event.getRecipients().iterator();
        while (iterator.hasNext()) {
            RPGPlayer rpgPlayer = RPG.getPlayer(iterator.next());
            if (rpgPlayer.isDialogueOpen()) {
                iterator.remove();
            }
        }
    }

    @EventHandler
    public void onNpcInteract(NPCRightClickEvent event) {
        Integer id = event.getNPC().getId();
        if (BaseActor.actors.containsKey(id)) {
            BaseActor actor = BaseActor.actors.get(id);
            actor.onInteract(RPG.getPlayer(event.getClicker()));
        }
    }
}
