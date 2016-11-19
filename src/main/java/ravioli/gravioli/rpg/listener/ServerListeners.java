package ravioli.gravioli.rpg.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.WorldInitEvent;
import ravioli.gravioli.rpg.RPG;

public class ServerListeners implements Listener {
    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        if (RPG.getPlayer(event.getPlayer()) == null) {
            RPG.addPlayer(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldLoad(WorldInitEvent event) {
        event.getWorld().setKeepSpawnInMemory(false);
    }
}
