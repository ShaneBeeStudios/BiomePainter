package com.shanebeestudios.bp.biome;

import com.google.common.collect.ImmutableSet;
import com.shanebeestudios.bp.image.ImageRender;
import org.bukkit.block.Biome;

public class BiomeGenerator implements nl.rutgerkok.worldgeneratorapi.BiomeGenerator {

    private final ImageRender imageRender;

    public BiomeGenerator(ImageRender imageRender) {
        this.imageRender = imageRender;
    }

    @Override
    public Biome getZoomedOutBiome(int x, int y, int z) {
        return imageRender.getBiome((x * 4) - 500, (z * 4) - 500);
    }

    @Override
    public ImmutableSet<Biome> getStructureBiomes() {
        return ImmutableSet.copyOf(VANILLA_OVERWORLD_STRUCTURE_BIOMES);
    }

}
