package ravioli.gravioli.rpg.world.instance.util;

import net.minecraft.server.v1_10_R1.*;

import java.io.IOException;

public class InstanceChunkLoader implements IChunkLoader, IAsyncChunkSaver {
    private final ChunkRegionLoader loadLoader;
    private final ChunkRegionLoader saveLoader;

    public InstanceChunkLoader(ChunkRegionLoader loadLoader, ChunkRegionLoader saveLoader) {
        this.loadLoader = loadLoader;
        this.saveLoader = saveLoader;
    }

    @Override
    public Chunk a(World world, int i, int j) throws IOException {
        if (this.saveLoader.chunkExists(world, i, j)) {
            return this.saveLoader.a(world, i, j);
        }
        return this.loadLoader.a(world, i, j);
    }

    @Override
    public void a(World world, Chunk chunk) throws IOException, ExceptionWorldConflict {
        this.saveLoader.a(world, chunk);
    }

    @Override
    public void b(World world, Chunk chunk) throws IOException {
        this.saveLoader.b(world, chunk);
    }

    @Override
    public void a() {}

    @Override
    public void b() {}

    @Override
    public boolean c() {
        return this.saveLoader.c();
    }
}
