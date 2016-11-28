package ravioli.gravioli.rpg.player.skill;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.util.ItemBuilder;

import java.util.HashSet;

public class SkillBackstab extends ActiveSkill {
    public SkillBackstab() {
        super(1, "Backstab", "Dash behind the player and deliver a swift strike to their spine casuing massive damage");
    }

    @Override
    public long getCooldown() {
        return 10000;
    }

    @Override
    public void use(RPGPlayer player) {
        Location eye = player.getPlayer().getEyeLocation();
        for (Entity entity : player.getPlayer().getNearbyEntities(5, 5, 5)) {
            if (entity instanceof LivingEntity) {
                Location entityLoc = entity.getLocation();
                Vector toEntity = ((LivingEntity) entity).getEyeLocation().toVector().subtract(eye.toVector());
                if (toEntity.normalize().dot(eye.getDirection()) > 0.99) {
                    float angle = entityLoc.getYaw() + 90;
                    Location loc = new Location(eye.getWorld(), entityLoc.getX() - Math.cos(Math.toRadians(angle)), entityLoc.getY(), entityLoc.getZ() - Math.sin(Math.toRadians(angle)), entityLoc.getYaw(), 0);
                    player.getPlayer().teleport(loc);
                }
            }
        }
    }

    @Override
    public ItemBuilder getItemRepresentation() {
        return new ItemBuilder(Material.SKULL_ITEM)
                .setName(this.getName())
                .setLore(this.getDescription());
    }

    @Override
    public int getLevelRequirement() {
        return 0;
    }
}
