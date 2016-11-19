package ravioli.gravioli.rpg.world.instance;

import org.bukkit.Bukkit;
import org.bukkit.World;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.player.RPGPlayer;
import ravioli.gravioli.rpg.util.WorldUtil;
import ravioli.gravioli.rpg.world.instance.util.InstanceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Instance {
    private ArrayList<RPGPlayer> players = new ArrayList<RPGPlayer>();

    private File sourceFolder;
    private World world;

    private String instanceName;
    private boolean destroyed;

    public Instance(File sourceFolder, String instanceName) {
        this.sourceFolder = sourceFolder;
        this.instanceName = instanceName;

        InstanceManager.addInstance(this);
    }

    public File getSourceFolder() {
        return this.sourceFolder;
    }

    public World getWorld() {
        return this.world;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public void create() {
        int worldIndex = 0;
        while (Bukkit.getWorld(this.instanceName + "_" + worldIndex) != null) {
            worldIndex++;
        }

        try {
            synchronized (InstanceUtil.INSTANCE_LOCK) {
                String worldName = this.instanceName + "_" + worldIndex;

                File instanceFolder = File.createTempFile(worldName, ".world", InstanceUtil.getInstanceWorldContainer());
                instanceFolder.delete();
                instanceFolder.mkdir();

                World world = InstanceUtil.createInstanceWorld(RPG.getPlugin(), this.sourceFolder, instanceFolder, worldName);
                this.world = world;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        // Delete the instance, remove all the players
        this.players.forEach(player -> {
            player.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
            player.setInstance(null);
        });
        this.destroyed = true;
    }

    public void addPlayer(RPGPlayer player) {
        this.players.add(player);
    }
}
