package ravioli.gravioli.rpg.world.generator;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.Vector;

public class VoidGenerator extends ChunkGenerator {
    private Biome biome = Biome.PLAINS;
    private final Vector spawn;

    public VoidGenerator(Biome biome, Vector spawn) {
        this.biome = biome;
        this.spawn = spawn;
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    @Override
    public byte[][] generateBlockSections(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biomes) {
        int maxHeight = world.getMaxHeight();
        byte[][] chunk = new byte[maxHeight >> 4][];

        int xPos = x * 16;
        int zPos = z * 16;

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                biomes.setBiome(i, j, biome);
            }
        }
        return chunk;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, spawn.getX(), spawn.getY(), spawn.getZ());
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return super.canSpawn(world, x, z);
    }
}