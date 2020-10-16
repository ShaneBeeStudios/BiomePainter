package com.shanebeestudios.bp;

import com.shanebeestudios.bp.biome.BiomeConfig;
import com.shanebeestudios.bp.command.WorldCmd;
import com.shanebeestudios.bp.world.WorldConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class BiomePainter extends JavaPlugin implements Listener {

    private static BiomePainter instance;
    private BiomeConfig biomeConfig;
    private WorldConfig worldConfig;

    @Override
    public void onEnable() {
        instance = this;

        // Load biomes and colors
        this.biomeConfig = new BiomeConfig(this);

        // Load world config and worlds
        this.worldConfig = new WorldConfig(this);

        loadCommands();
    }

    @Override
    public void onDisable() {
        instance = null;
        this.biomeConfig = null;
        this.worldConfig = null;
    }

    public static BiomePainter getInstance() {
        return instance;
    }

    private void loadCommands() {
        new WorldCmd(this);
    }

    public BiomeConfig getBiomeConfig() {
        return biomeConfig;
    }

    public WorldConfig getWorldConfig() {
        return worldConfig;
    }

}
