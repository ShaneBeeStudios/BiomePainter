package com.shanebeestudios.bp.util;

import org.bukkit.World.Environment;
import org.bukkit.WorldType;

import java.util.Random;

public class WorldUtil {

    /**
     * Get an {@link Environment} from a String
     *
     * @param name String version of environment
     * @return Environment matched to string. If no match, NORMAL is returned
     */
    public static Environment getEnvironment(String name) {
        Environment environment = Environment.NORMAL;
        try {
            environment = Environment.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ignore) {
        }
        return environment;
    }

    public static WorldType getWorldType(String name) {
        WorldType worldType = WorldType.getByName(name);
        return worldType != null ? worldType : WorldType.NORMAL;
    }

    public static long getSeed(String name) {
        if (name != null) {
            if (isLong(name)) {
                return Long.parseLong(name);
            }
            return name.hashCode();
        }
        return new Random().nextLong();
    }

    private static boolean isLong(String name) {
        try {
            Long.parseLong(name);
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

}
