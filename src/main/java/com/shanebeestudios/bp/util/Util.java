package com.shanebeestudios.bp.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class Util {

    private static final String PREFIX = "&7[&3Biome&bPainter&7] ";
    private static final Logger LOGGER = Bukkit.getLogger();

    public static void log(String log) {
        LOGGER.info(getColString(PREFIX + log));
    }

    public static void log(String format, Object... objects) {
        log(String.format(format, objects));
    }

    public static void warn(String warning) {
        LOGGER.warning(getColString(PREFIX + "&e" + warning));
    }

    public static void warn(String format, Object... objects) {
        warn(String.format(format, objects));
    }

    public static void error(String error) {
        LOGGER.severe(getColString(PREFIX + "&c" + error));
    }

    public static void error(String format, Object... objects) {
        error(String.format(format, objects));
    }

    public static void sendMsg(CommandSender receiver, String message) {
        receiver.sendMessage(getColString(PREFIX + message));
    }

    public static String getColString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static int getInt(String stringInt, int defaultInt) {
        int i = defaultInt;
        try {
            i = Integer.parseInt(stringInt);
        } catch (NumberFormatException ignore) {
        }
        return i;
    }


}
