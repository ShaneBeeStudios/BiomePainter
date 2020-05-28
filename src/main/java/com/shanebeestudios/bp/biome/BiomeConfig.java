package com.shanebeestudios.bp.biome;

import com.shanebeestudios.bp.BiomePainter;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.*;
import java.io.File;

/**
 * Config for {@link BiomeMap BiomeMaps}
 * <p><b>NOTE:</b> Used internally</p>
 */
public class BiomeConfig {

    private final BiomePainter plugin;
    private File file;

    public BiomeConfig(BiomePainter plugin) {
        this.plugin = plugin;
        loadConfigFile();
    }

    private void loadConfigFile() {
        if (file == null) {
            file = new File(plugin.getDataFolder(), "biomes.yml");
        }
        if (!file.exists()) {
            plugin.saveResource("biomes.yml", false);
        }
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section = fileConfig.getConfigurationSection("biomes");
        if (section != null) {
            for (String key : section.getKeys(false)) {
                Biome biome = getBiome(key);
                String c = section.getString(key);
                Color color = Color.BLACK;
                if (c != null) {
                    color = getColor(c);
                }
                BiomeMap.addBiome(biome, color);
            }
        }
    }

    private Biome getBiome(String biome) {
        try {
            return Biome.valueOf(biome.toUpperCase());
        } catch (Exception ignore) {
            return Biome.PLAINS;
        }
    }

    private Color getColor(String color) {
        String[] split = color.split(":");
        if (split.length != 3) return Color.BLACK;

        try {
            int r = Integer.parseInt(split[0]);
            int g = Integer.parseInt(split[1]);
            int b = Integer.parseInt(split[2]);
            return new Color(r, g, b);
        } catch (Exception ignore) {
            return Color.BLACK;
        }
    }

}
