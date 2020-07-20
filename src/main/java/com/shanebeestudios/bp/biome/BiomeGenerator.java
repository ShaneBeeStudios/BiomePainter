package com.shanebeestudios.bp.biome;

import com.google.common.collect.ImmutableSet;
import com.shanebeestudios.bp.image.ImageRender;
import org.bukkit.World.Environment;
import org.bukkit.block.Biome;

public class BiomeGenerator implements nl.rutgerkok.worldgeneratorapi.BiomeGenerator {

    private final ImageRender imageRender;
    private final Environment environment;

    public BiomeGenerator(ImageRender imageRender, Environment environment) {
        this.imageRender = imageRender;
        this.environment = environment;
    }

    @Override
    public Biome getZoomedOutBiome(int x, int y, int z) {
        return imageRender.getBiome((x * 4) - 500, (z * 4) - 500, environment);
    }

    @Override
    public ImmutableSet<Biome> getStructureBiomes() {
        return ImmutableSet.copyOf(VANILLA_OVERWORLD_STRUCTURE_BIOMES);
    }

}
