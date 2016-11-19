package ravioli.gravioli.rpg.actor;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import ravioli.gravioli.rpg.player.RPGPlayer;

import java.util.HashMap;
import java.util.UUID;

public abstract class BaseActor {
    public static final HashMap<Integer, BaseActor> actors = new HashMap();
    public static final BaseActor CLASS_SELECTION = new Actor001ClassSelection();
    public static final BaseActor TUTORIAL_GUIDE = new Actor002TutorialGuide();

    protected NPC entity;

    public BaseActor(int id) {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        Bukkit.getWorlds().get(0).getEntities().stream().filter(registry::isNPC).forEach(entity -> {
            NPC npc = registry.getNPC(entity);
            if (npc.getId() == id) {
                this.entity = npc;
            }
        });
        if (this.entity == null) {
            this.entity = registry.createNPC(EntityType.PLAYER, UUID.nameUUIDFromBytes(this.getName().getBytes()), id, this.getName());
        }
        actors.put(Integer.valueOf(id), this);
    }

    public void spawn(Location location) {
        this.entity.spawn(location);
    }

    public void despawn() {
        this.entity.despawn();
    }

    public Location getLocation() {
        return this.entity.getStoredLocation();
    }

    public int getActorId() {
        return this.entity.getId();
    }

    public void onInteract(RPGPlayer player) {}
    public abstract String getName();
}
