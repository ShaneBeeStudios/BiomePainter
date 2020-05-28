package com.shanebeestudios.bp.world;

import com.shanebeestudios.bp.BiomePainter;
import com.shanebeestudios.bp.biome.BiomeGenerator;
import com.shanebeestudios.bp.image.ImageRender;
import com.shanebeestudios.bp.util.WorldUtil;
import nl.rutgerkok.worldgeneratorapi.event.WorldGeneratorInitEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class WorldGenerator implements Listener, ConfigurationSerializable {

    private final String name;
    private World world;
    private final Environment environment;
    private final WorldType worldType;
    private final ImageRender imageRender;
    private final int scale;
    private boolean spawnMemory;

    public WorldGenerator(String name, Environment environment, WorldType worldType, ImageRender imageRender, int scale) {
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

    public void createWorld() {
        spawnMemory = false;
        this.world = WorldCreator
                .name(name)
                .environment(environment)
                .type(worldType)
                .generateStructures(false)
                .createWorld();
        assert world != null;
        world.setSpawnLocation(getCenter());
    }

    public void loadWorld() {
        this.world = WorldCreator
                .name(name)
                .environment(environment)
                .type(worldType)
                .generateStructures(false)
                .createWorld();
    }

    @EventHandler
    public void onWorldLoad(WorldGeneratorInitEvent event) {
        World world = event.getWorld();
        if (!spawnMemory) {
            world.setKeepSpawnInMemory(false);
        }
        if (world.getName().equalsIgnoreCase(name)) {
            nl.rutgerkok.worldgeneratorapi.WorldGenerator worldGenerator = event.getWorldGenerator();
            worldGenerator.setBiomeGenerator(new BiomeGenerator(imageRender));
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

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("name", name);
        result.put("environment", environment.toString());
        result.put("type", worldType.getName());
        result.put("image", imageRender != null ? imageRender.getFileName() : "none");
        result.put("scale", scale);

        return result;
    }

    public static WorldGenerator deserialize(Map<String, Object> args) {
        String name = (String) args.get("name");
        Environment environment = WorldUtil.getEnvironment((String) args.get("environment"));
        WorldType worldType = WorldUtil.getWorldType((String) args.get("type"));
        String image = (String) args.get("image");
        ImageRender imageRender = null;
        int scale = ((int) args.get("scale"));
        if (!image.equalsIgnoreCase("none")) {
            imageRender = new ImageRender(image, scale);
        }

        return new WorldGenerator(name, environment, worldType, imageRender, scale);
    }

}
