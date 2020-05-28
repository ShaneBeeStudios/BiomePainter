package com.shanebeestudios.bp.world;

import com.shanebeestudios.bp.BiomePainter;
import com.shanebeestudios.bp.util.Util;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class WorldConfig {

    public static final List<String> IMAGE_FILES = new ArrayList<>();

    private final BiomePainter plugin;
    private File file;
    private FileConfiguration config;

    public WorldConfig(BiomePainter plugin) {
        this.plugin = plugin;
        loadConfigFile();
        loadWorlds();
    }

    private void loadConfigFile() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), "worlds.yml");
        }
        if (!file.exists()) {
            plugin.saveResource("worlds.yml", false);
        }
        File imageFolder = new File(plugin.getDataFolder(), "/images");
        if (!imageFolder.isDirectory()) {
            imageFolder.mkdirs();
        }
        for (File f : imageFolder.listFiles()) {
            String name = f.getName();
            if (name.contains(".jpg") || name.contains(".png")) {
                IMAGE_FILES.add(name);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void loadWorlds() {
        ConfigurationSection section = config.getConfigurationSection("worlds");
        if (section == null) return;

        Set<String> keys = section.getKeys(false);
        Util.log("Loading custom worlds:");
        for (String key : keys) {
            Util.log(" - &b" + key);
        }

        for (String key : keys) {
            Object object = config.get("worlds." + key);
            if (object instanceof WorldGenerator) {
                WorldGenerator worldGenerator = ((WorldGenerator) object);
                worldGenerator.loadWorld();
                Util.log("&aSuccessfully loaded world: &b" + key);
            } else {
                Util.log("&cUnable to load world: &b" + key);
            }
        }


    }

    public boolean saveWorld(WorldGenerator worldGenerator) {
        config.set("worlds." + worldGenerator.getName(), worldGenerator);
        return save();
    }

    private boolean save() {
        try {
            config.save(file);
            return true;
        } catch (IOException ignore) {
            return false;
        }
    }


}
