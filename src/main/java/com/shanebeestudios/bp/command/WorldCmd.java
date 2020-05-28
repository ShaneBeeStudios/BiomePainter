package com.shanebeestudios.bp.command;

import com.shanebeestudios.bp.BiomePainter;
import com.shanebeestudios.bp.image.ImageRender;
import com.shanebeestudios.bp.util.Util;
import com.shanebeestudios.bp.util.WorldUtil;
import com.shanebeestudios.bp.world.WorldConfig;
import com.shanebeestudios.bp.world.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorldCmd extends BaseCmd {

    private final List<String> SUB_COMMANDS = Arrays.asList("tp", "create");
    private final WorldConfig worldConfig;

    public WorldCmd(BiomePainter plugin) {
        super(plugin, "world");
        this.worldConfig = plugin.getWorldConfig();
    }

    @Override
    boolean onCommand(CommandSender sender, String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "create":
                    //create world
                    // world create NAME ENV TYPE FILE SCALE
                    if (args.length > 3) {
                        String fileName = null;
                        if (args.length >= 5) {
                            fileName = args[4];
                        }

                        int scale = 4;
                        if (args.length == 6) {
                            scale = Util.getInt(args[5], 4);
                        }

                        String worldName = args[1];
                        if (Bukkit.getWorld(worldName) != null) {
                            Util.sendMsg(sender, "&cWorld &b" + worldName + "&c already exists!");
                            return true;
                        }
                        if (createWorld(sender, worldName, args[2], args[3], fileName, scale)) {
                            Util.sendMsg(sender, "&aSuccessfully &7created world &b" + worldName + "&7 with scale: &b" + scale);
                        } else {
                            Util.sendMsg(sender, "&cUnable to create world &b" + worldName);
                        }
                    } else {
                        Util.sendMsg(sender,
                                "&6Correct usage: &b/world create <world name> <environment> <world type> [image file name] [scale]");
                    }
                    break;
                case "tp":
                    if (args.length == 2) {
                        World world = Bukkit.getWorld(args[1]);
                        if (sender instanceof Player && world != null) {
                            ((Player) sender).teleport(world.getSpawnLocation());
                        }
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> matches = new ArrayList<>();
            for (String string : SUB_COMMANDS) {
                if (StringUtil.startsWithIgnoreCase(string, args[0])) {
                    matches.add(string);
                }
            }
            return matches;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("tp")) {
            List<String> worldList = new ArrayList<>();
            for (World world : Bukkit.getWorlds()) {
                String name = world.getName();
                if (StringUtil.startsWithIgnoreCase(name, args[1])) {
                    worldList.add(name);
                }
            }
            return worldList;
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("create")) {
            if (args.length == 2) {
                return Collections.singletonList("<world name>");
            } else if (args.length == 3) {
                List<String> envList = new ArrayList<>();
                for (Environment environment : Environment.values()) {
                    String name = environment.toString();
                    if (StringUtil.startsWithIgnoreCase(name, args[2])) {
                        envList.add(name);
                    }
                }
                return envList;
            } else if (args.length == 4) {
                List<String> typeList = new ArrayList<>();
                for (WorldType worldType : WorldType.values()) {
                    String name = worldType.toString();
                    if (StringUtil.startsWithIgnoreCase(name, args[3])) {
                        typeList.add(name);
                    }
                }
                return typeList;
            } else if (args.length == 5) {
                if (WorldConfig.IMAGE_FILES.size() > 1) {
                    List<String> imageFiles = new ArrayList<>();
                    for (String imageName : WorldConfig.IMAGE_FILES) {
                        if (StringUtil.startsWithIgnoreCase(imageName, args[4])) {
                            imageFiles.add(imageName);
                        }
                    }
                    return imageFiles;
                }
                return Collections.singletonList("<image file>");
            } else if (args.length == 6) {
                return Collections.singletonList("<scale>");
            }
        }
        return null;
    }

    private boolean createWorld(CommandSender sender, String worldName, String environmentName, String worldTypeName, String fileName, int scale) {
        Environment environment = WorldUtil.getEnvironment(environmentName);
        WorldType worldType = WorldUtil.getWorldType(worldTypeName);
        ImageRender imageRender = null;
        if (fileName != null) {
            Util.sendMsg(sender, "&7Generating biome map for image: &b" + fileName);
            imageRender = new ImageRender(fileName, scale);
            Util.sendMsg(sender, "&aSuccessfully &7generated biome map.");
        }
        Util.sendMsg(sender, "Generating world: &b" + worldName);
        WorldGenerator worldGenerator = new WorldGenerator(worldName, environment, worldType,imageRender, scale);
        worldGenerator.createWorld();
        if (worldGenerator.isValid()) {
            return worldConfig.saveWorld(worldGenerator);
        }
        return false;
    }

}
