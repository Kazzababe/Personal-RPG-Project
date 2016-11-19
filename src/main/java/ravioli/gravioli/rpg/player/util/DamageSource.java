package ravioli.gravioli.rpg.player.util;

import org.bukkit.entity.EntityType;

public class DamageSource {
    private String damager;
    private EntityType damagerType;

    public static final DamageSource FALL_DAMAGE = new DamageSource("Fall damage");

    public DamageSource(String damager) {
        this(damager, EntityType.UNKNOWN);
    }

    public DamageSource(String damager, EntityType damagerType) {
        this.damager = damager;
        this.damagerType = damagerType;
    }

    public String getDamager() {
        return this.damager;
    }

    public EntityType getDamagerType() {
        return this.damagerType;
    }
}
