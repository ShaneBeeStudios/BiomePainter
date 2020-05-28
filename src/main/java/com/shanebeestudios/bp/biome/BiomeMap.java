package com.shanebeestudios.bp.biome;

import com.shanebeestudios.bp.util.Util;
import org.bukkit.block.Biome;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a {@link Biome} which correlates to a {@link Color}
 */
@SuppressWarnings("unused")
public class BiomeMap {

    private static final Map<Color, BiomeMap> BY_COLOR = new HashMap<>();

    static void addBiome(Biome biome, Color color) {
        if (BY_COLOR.containsKey(color)) {
            String c = "" + color.getRed() + ":" + color.getGreen() + ":" + color.getBlue();
            Util.log("&cBiomeMap already contains color: &b'" + c + "'&c, Consider using another color for &b" + biome.toString());
            return;
        }
        BiomeMap biomeMap = new BiomeMap(biome, color);
        BY_COLOR.put(color, biomeMap);
    }

    /**
     * Get a BiomeMap by {@link Color}
     *
     * @param color Color to grab BiomeMap for
     * @return BiomeMap from color
     */
    public static BiomeMap getByColor(Color color) {
        if (BY_COLOR.containsKey(color)) {
            return BY_COLOR.get(color);
        }
        return null;
    }

    private final Biome biome;
    private final Color color;

    BiomeMap(Biome biome, Color color) {
        this.biome = biome;
        this.color = color;
    }

    /**
     * Get the biome of this BiomeMap
     *
     * @return Biome of this BiomeMap
     */
    public Biome getBiome() {
        return biome;
    }

    /**
     * Get the color of this BiomeMap
     *
     * @return Color of this BiomeMap
     */
    public Color getColor() {
        return color;
    }

}
