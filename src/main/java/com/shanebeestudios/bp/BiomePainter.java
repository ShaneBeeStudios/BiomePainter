package com.shanebeestudios.bp;

import com.shanebeestudios.bp.biome.BiomeConfig;
import com.shanebeestudios.bp.command.WorldCmd;
import com.shanebeestudios.bp.world.WorldConfig;
import com.shanebeestudios.bp.world.WorldGenerator;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class BiomePainter extends JavaPlugin implements Listener {

    static {
        ConfigurationSerialization.registerClass(WorldGenerator.class, "worldgenerator");
    }

    private static BiomePainter instance;
    private WorldConfig worldConfig;

    @Override
    public void onEnable() {
        instance = this;

        // Load biomes and colors
        new BiomeConfig(this);

        // Load world config and worlds
        this.worldConfig = new WorldConfig(this);

        loadCommands();
    }

    public static BiomePainter getInstance() {
        return instance;
    }

    private void loadCommands() {
        new WorldCmd(this);
    }

    public WorldConfig getWorldConfig() {
        return worldConfig;
    }

}
