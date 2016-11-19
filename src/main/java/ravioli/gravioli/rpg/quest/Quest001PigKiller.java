package ravioli.gravioli.rpg.quest;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import ravioli.gravioli.rpg.player.RPGPlayer;

public class Quest001PigKiller extends Quest {
    public Quest001PigKiller(RPGPlayer player) {
        super(player);

        this.addObjective(0, "Go kill 3 pigs");
        this.addObjective(1, "Cook and eat the pigs meat");

        this.setData("pigsKilled", 0);
        this.setData("homies", false);
        this.setData("cats", "free");
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return "Pig Killer";
    }

    @EventHandler
    public void onEntityKilled(EntityDeathEvent event) {
        if (this.getStage() == 0) {
            LivingEntity entity = event.getEntity();
            if (entity instanceof Pig) {
                if (entity.getKiller() != null && entity.getKiller() instanceof Player) {
                    Player player = (Player) entity.getKiller();
                    if (player.getUniqueId().equals(this.getOwningPlayer().getUniqueId())) {
                        int pigsKilled = (int) this.getData("pigsKilled");
                        this.setData("pigsKilled", Math.min(pigsKilled + 1, 3));

                        if (pigsKilled >= 2) {
                            this.setStage(1);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        if (event.getPlayer().getUniqueId().equals(this.getOwningPlayer().getUniqueId())) {
            if (event.getItem().getType() == Material.GRILLED_PORK) {
                if (this.getStage() == 1) {
                    this.getOwningPlayer().getPlayer().sendMessage("Lol you da best, you finish da quest");
                    this.getOwningPlayer().completeQuest(this);
                }
            }
        }
    }
}