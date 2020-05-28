package com.shanebeestudios.bp.command;

import com.shanebeestudios.bp.BiomePainter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;

import java.util.List;

@SuppressWarnings({"NullableProblems", "unused"})
public abstract class BaseCmd implements TabExecutor {

    BaseCmd(BiomePainter plugin, String commandString) {
        PluginCommand command = plugin.getCommand(commandString);
        assert command != null;
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return onCommand(sender, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return onTabComplete(sender, args);
    }

    abstract boolean onCommand(CommandSender sender, String[] args);

    abstract List<String> onTabComplete(CommandSender sender, String[] args);

}
