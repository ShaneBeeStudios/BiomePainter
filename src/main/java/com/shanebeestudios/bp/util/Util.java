package com.shanebeestudios.bp.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Util {

    private static String PREFIX = "&7[&3Biome&bPainter&7] ";

    public static void log(String log) {
        Bukkit.getConsoleSender().sendMessage(getColString(PREFIX + log));
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
