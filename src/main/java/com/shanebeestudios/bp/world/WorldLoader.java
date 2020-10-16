package com.shanebeestudios.bp.world;

import com.shanebeestudios.bp.image.ImageRender;
import com.shanebeestudios.bp.util.Util;
import com.shanebeestudios.bp.util.WorldUtil;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.FileConfiguration;

public class WorldLoader {

    private final FileConfiguration config;

    public WorldLoader(FileConfiguration config) {
        this.config = config;
    }

    public boolean loadWorld(String worldName) {
        String path = "worlds." + worldName + ".";
        WorldBuilder worldBuilder = new WorldBuilder();
        worldBuilder.setName(worldName);

        // Load environment
        if (config.isSet(path + "environment")) {
            String envString = config.getString(path + "environment");
            assert envString != null;
            World.Environment environment = WorldUtil.getEnvironment(envString);
            if (environment != null) {
                worldBuilder.setEnvironment(environment);
            } else {
                Util.error("Error loading world '&7%s&c', unknown environment: &7%s", worldName, envString);
                return false;
            }
        } else {
            Util.error("Error loading world '&7%s&c', missing environment", worldName);
            return false;
        }

        // Load world type
        if (config.isSet(path + "world-type")) {
            String worldTypeString = config.getString(path + "world-type");
            assert worldTypeString != null;
            WorldType worldType = WorldUtil.getWorldType(worldTypeString);
            if (worldType != null) {
                worldBuilder.setWorldType(worldType);
            } else {
                Util.error("Error: Loading world '&7%s&c', unknown world type: &7%s", worldName, worldTypeString);
                return false;
            }
        } else {
            Util.error("Error loading world '&7%s&c', missing world type", worldName);
            return false;
        }

        // Load seed
        if (config.isSet(path + "seed")) {
            long seed = config.getLong(path + "seed");
            worldBuilder.setSeed(seed);
        }

        // Load spawn in memory
        boolean spawnMemory = true;
        if (config.isSet(path + "keep-spawn-in-memory")) {
            spawnMemory = config.getBoolean(path + "keep-spawn-in-memory");
        }
        worldBuilder.setSpawnMemory(spawnMemory);

        // Load image renderer
        if (config.isSet(path + "image")) {
            // Load scale
            int scale = 1;
            if (config.isSet(path + "scale")) {
                scale = config.getInt(path + "scale");
            } else {
                Util.warn("Issue loading world '&7%s&e', missing scale, defaulting to 1", worldName);
            }
            worldBuilder.setScale(scale);

            // Load image
            String imageString = config.getString(path + "image");
            assert imageString != null;
            if (!WorldConfig.IMAGE_FILES.contains(imageString)) {
                Util.error("Error loading world '&7%s&c', image file '&7%s&c' can not be found", worldName, imageString);
                return false;
            }
            ImageRender imageRender = new ImageRender(imageString, scale);
            worldBuilder.setImageRender(imageRender);
        }
        
        // Load world if everything passed
        worldBuilder.loadWorld();
        return true;
    }

    public void saveWorld(WorldBuilder worldBuilder) {
        String path = "worlds." + worldBuilder.getName() + ".";
        config.set(path + "environment", worldBuilder.getEnvironment().toString());
        config.set(path + "world-type", worldBuilder.getWorldType().toString());
        config.set(path + "seed", worldBuilder.getSeed());
        config.set(path + "keep-spawn-in-memory", worldBuilder.isSpawnMemory());
        if (worldBuilder.getImageRender() != null) {
            config.set(path + "scale", worldBuilder.getScale());
            config.set(path + "image", worldBuilder.getImageRender().getFileName());
        }
    }

}
