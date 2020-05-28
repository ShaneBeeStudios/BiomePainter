package com.shanebeestudios.bp.image;

import com.shanebeestudios.bp.BiomePainter;
import com.shanebeestudios.bp.biome.BiomeMap;
import org.bukkit.block.Biome;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Render an image to create a map of {@link Biome Biomes} based on pixel color
 */
public class ImageRender {

    private final String fileName;
    private boolean valid = false;
    private final BufferedImage bufferedImage;
    private final Biome[][] biomes;
    private final int width;
    private final int height;
    private final int scale;

    public ImageRender(String fileName, int scale) {
        this.fileName = fileName;
        BufferedImage bufferedImage1 = null;
        try {
            File file = new File(BiomePainter.getInstance().getDataFolder() + "/images", fileName);
            InputStream stream = new FileInputStream(file);
            bufferedImage1 = ImageIO.read(stream);
            stream.close();
            valid = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedImage = bufferedImage1;
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        biomes = createBiomeMap();
        this.scale = scale;
        bufferedImage.flush();
        bufferedImage1.flush();
    }

    private Color getColorAtPosition(int x, int y) {
        if (bufferedImage == null) return Color.BLACK;

        if (x < 0 || y < 0) return Color.BLACK;
        if (x > bufferedImage.getWidth() || y > bufferedImage.getHeight()) return Color.BLACK;

        int rgb = bufferedImage.getRGB(x, y);
        int blue = rgb & 0xff;
        int green = (rgb & 0xff00) >> 8;
        int red = (rgb & 0xff0000) >> 16;
        return new Color(red, green, blue);
    }

    private Biome getBiomeAtPosition(int x, int y) {
        Color color = getColorAtPosition(x, y);
        if (color != null) {
            BiomeMap biomeMap = BiomeMap.getByColor(color);
            if (biomeMap != null) {
                return biomeMap.getBiome();
            }
        }
        return Biome.OCEAN;
    }

    private Biome[][] createBiomeMap() {
        Biome[][] map = new Biome[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = getBiomeAtPosition(x, y);
            }
        }
        return map;
    }

    public boolean isValid() {
        return valid;
    }

    public String getFileName() {
        return fileName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Get a {@link Biome} at a specific position on the image
     *
     * @param x X position on the image
     * @param y Y position on the image
     * @param defaultBiome Default biome to return if none found
     * @return A Biome based on the color of the pixel of the image
     */
    public Biome getBiome(int x, int y, Biome defaultBiome) {
        int X = x * scale;
        int Y = y * scale;

        if (X < 0 || Y < 0) return defaultBiome;
        if (X > (width - 1) || Y > (height - 1)) return defaultBiome;
        if (biomes[X][Y] != null) {
            return biomes[X][Y];
        }
        return defaultBiome;
    }

    /**
     * Get a {@link Biome} at a specific position on the image
     *
     * @param x X position on the image
     * @param y Y position on the image
     * @return A Biome based on the color of the pixel of the image
     * <br><b>NOTE:</b> If a biome does not match, {@link Biome#OCEAN} will return.
     */
    public Biome getBiome(int x, int y) {
        return getBiome(x, y, Biome.PLAINS);
    }

}
