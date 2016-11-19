package ravioli.gravioli.rpg.world.instance.util;

import net.minecraft.server.v1_10_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import ravioli.gravioli.rpg.RPG;
import ravioli.gravioli.rpg.util.WorldUtil;
import ravioli.gravioli.rpg.world.generator.VoidGenerator;

import java.io.File;

public class InstanceUtil {
    public static final Object INSTANCE_LOCK = new Object();

    public static org.bukkit.World createInstanceWorld(Plugin plugin, File source, File target, String instanceName) {
        MinecraftServer console = ((CraftServer) plugin.getServer()).getServer();
        IDataManager dataManager = new InstanceDataManager(RPG.getPlugin(), instanceName, source, target, console.getDataConverterManager());

        int dimension = 10 + console.worlds.size();

        boolean used = false;
        do {
            for (WorldServer server : console.worlds) {
                used = server.dimension == dimension;
                if (used) {
                    dimension++;
                    break;
                }
            }
        } while (used);

        WorldData wd = dataManager.getWorldData();
        ChunkGenerator generator = new VoidGenerator(Biome.PLAINS, new Vector(wd.b(), wd.c(), wd.d()));

        WorldServer instance = (WorldServer) new WorldServer(console, dataManager, wd, dimension, console.methodProfiler, World.Environment.NORMAL, generator).b();

        instance.tracker = new EntityTracker(instance);
        instance.addIWorldAccess((IWorldAccess) new WorldManager(console, instance));
        instance.getWorldData().setDifficulty(EnumDifficulty.values()[Difficulty.NORMAL.ordinal()]);
        instance.setSpawnFlags(true, true);
        instance.keepSpawnInMemory = false;
        console.worlds.add(instance);

        instance.getWorld().getPopulators().addAll(generator.getDefaultPopulators(instance.getWorld()));

        plugin.getServer().getPluginManager().callEvent(new WorldInitEvent(instance.getWorld()));
        plugin.getServer().getPluginManager().callEvent(new WorldLoadEvent(instance.getWorld()));

        return instance.getWorld();
    }

    public static File getInstanceWorldContainer() {
        File folder = new File(Bukkit.getWorldContainer(), "worlds");
        folder.mkdir();
        return folder;
    }
}
