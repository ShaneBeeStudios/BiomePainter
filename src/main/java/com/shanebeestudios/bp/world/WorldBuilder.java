package com.shanebeestudios.bp.world;

import com.shanebeestudios.bp.BiomePainter;
import com.shanebeestudios.bp.biome.BiomeGenerator;
import com.shanebeestudios.bp.image.ImageRender;
import nl.rutgerkok.worldgeneratorapi.WorldGenerator;
import nl.rutgerkok.worldgeneratorapi.event.WorldGeneratorInitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@SuppressWarnings("unused")
public class WorldBuilder implements Listener {

    private String name;
    private World world;
    private Environment environment;
    private WorldType worldType;
    private long seed;
    private ImageRender imageRender;
    private int scale;
    private boolean spawnMemory;

    public WorldBuilder(String name, Environment environment, WorldType worldType, ImageRender imageRender, int scale) {
        ImageRender imageRender1;
        this.name = name;
        imageRender1 = imageRender;
        this.environment = environment;
        this.worldType = worldType;
        if (imageRender != null) {
            if (imageRender.isValid()) {
                Bukkit.getPluginManager().registerEvents(this, BiomePainter.getInstance());
            } else {
                imageRender1 = null;
            }
        }
        this.imageRender = imageRender1;
        this.scale = scale;
    }

    public WorldBuilder() {
    }

    public void createWorld() {
        this.world = WorldCreator
                .name(name)
                .environment(environment)
                .type(worldType)
                .generateStructures(true)
                .createWorld();
        assert world != null;
        this.world.setSpawnLocation(getCenter());
        this.world.setKeepSpawnInMemory(spawnMemory);
    }

    public void loadWorld() {
        this.world = WorldCreator
                .name(name)
                .environment(environment)
                .type(worldType)
                .seed(seed)
                .generateStructures(true)
                .createWorld();
        assert this.world != null;
        this.world.setKeepSpawnInMemory(spawnMemory);
    }

    @EventHandler
    public void onWorldLoad(WorldGeneratorInitEvent event) {
        World world = event.getWorld();
        if (world.getName().equalsIgnoreCase(name)) {
            WorldGenerator worldGenerator = event.getWorldGenerator();
            worldGenerator.setBiomeGenerator(new BiomeGenerator(imageRender, world.getEnvironment()));
        }
    }

    private Location getCenter() {
        if (imageRender == null) {
            return world.getHighestBlockAt(1, 1).getLocation();
        }
        int w = imageRender.getWidth();
        int h = imageRender.getHeight();

        int x = ((w / 2) / scale) + 500;
        int z = ((h / 2) / scale) + 500;
        return world.getHighestBlockAt(x, z).getLocation();
    }

    public String getName() {
        return name;
    }

    public ImageRender getImageRender() {
        return imageRender;
    }

    public World getWorld() {
        return world;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public int getScale() {
        return scale;
    }

    public boolean isValid() {
        return world != null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public WorldType getWorldType() {
        return worldType;
    }

    public void setWorldType(WorldType worldType) {
        this.worldType = worldType;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public void setImageRender(ImageRender imageRender) {
        this.imageRender = imageRender;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isSpawnMemory() {
        return spawnMemory;
    }

    public void setSpawnMemory(boolean spawnMemory) {
        this.spawnMemory = spawnMemory;
    }
}
