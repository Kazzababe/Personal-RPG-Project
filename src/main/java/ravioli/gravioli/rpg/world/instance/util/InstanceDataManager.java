package ravioli.gravioli.rpg.world.instance.util;

import com.google.common.io.Files;
import net.minecraft.server.v1_10_R1.*;
import org.bukkit.plugin.Plugin;
import ravioli.gravioli.rpg.util.WorldUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class InstanceDataManager extends ServerNBTManager {
    private static final String WORLD_DATA = "level.dat";
    private static final String WORLD_DATA_OLD = "level.dat_old";

    private final Plugin instances;
    private final File loadDataFolder;
    private final String world;

    public InstanceDataManager(Plugin instances, String instanceName, File loadDataFolder, File saveDataFolder, DataConverterManager dataConverterManager) {
        // false flag - do not create players directory.
        super(saveDataFolder.getParentFile(), saveDataFolder.getName(), false, dataConverterManager);
        this.instances = instances;
        this.loadDataFolder = loadDataFolder;
        this.world = instanceName;
    }

    @Override
    public WorldData getWorldData() {
        File file1 = new File(loadDataFolder, WORLD_DATA);
        NBTTagCompound nbttagcompound;
        NBTTagCompound nbttagcompound1;
        WorldData result = null;
        if (file1.exists()) {
            try {
                nbttagcompound = NBTCompressedStreamTools.a((InputStream) (new FileInputStream(file1)));
                nbttagcompound1 = nbttagcompound.getCompound("Data");
                result = new WorldData(nbttagcompound1);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            file1 = new File(loadDataFolder, WORLD_DATA_OLD);
            if (file1.exists()) {
                try {
                    nbttagcompound = NBTCompressedStreamTools.a((InputStream) (new FileInputStream(file1)));
                    nbttagcompound1 = nbttagcompound.getCompound("Data");
                    result = new WorldData(nbttagcompound1);
                } catch (Exception exception1) {
                    exception1.printStackTrace();
                }
            }
        }
        if (result != null) {
            result.a(world);
        }
        return result;
    }

    @Override
    public IChunkLoader createChunkLoader(WorldProvider wp) {
        File loadChunkDir;
        File saveChunkDir;
        if (wp instanceof WorldProviderHell) {
            loadChunkDir = new File(loadDataFolder, "DIM-1");
            saveChunkDir = new File(getDirectory(), "DIM-1");
        } else if (wp instanceof WorldProviderTheEnd) {
            loadChunkDir = new File(loadDataFolder, "DIM1");
            saveChunkDir = new File(getDirectory(), "DIM1");
        } else {
            loadChunkDir = loadDataFolder;
            saveChunkDir = getDirectory();
        }
        ChunkRegionLoader loadLoader = new ChunkRegionLoader(loadChunkDir, this.a);
        ChunkRegionLoader saveLoader = new ChunkRegionLoader(saveChunkDir, this.a);
        return new InstanceChunkLoader(loadLoader, saveLoader);
    }

    @Override
    public File getDataFile(String string) {
        File result = new File(this.loadDataFolder, string + ".dat");
        if (result.isFile()) {
            return result;
        }
        File source = new File(getDirectory(), string + ".dat");
        if (!source.isFile()) {
            return result;
        }
        try {
            Files.copy(source, result);
        } catch (IOException ex) {
            instances.getLogger().log(Level.SEVERE, "Error copying " + source.getPath() + " to " + result.getPath() + " for Instance world: " + world, ex);
        }
        return result;
    }
}
