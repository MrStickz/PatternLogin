package me.mrstick.patternLogin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandHandler implements CommandExecutor, TabCompleter {

    private final HashMap<String, CommandInterface> commands = new HashMap<>();

    public void register(String name, CommandInterface cmd) {
        commands.put(name, cmd);
    }

    public boolean exists(String name) {
        return commands.containsKey(name);
    }

    public CommandInterface getExecutor(String name) {
        return commands.get(name);
    }


    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                return commands.keySet().stream()
                        .filter(name -> name.startsWith(args[0]) && !name.equalsIgnoreCase(cmd.getName()))
                        .collect(Collectors.toList());
            } else if (args.length > 1) {
                String subCommandName = args[0];
                TabCompleter tabCompleter = getTabCompleter(subCommandName);
                if (tabCompleter != null) {
                    return tabCompleter.onTabComplete(sender, cmd, alias, args);
                }
            }
        }
        return null;
    }

    private TabCompleter getTabCompleter(String name) {
        if (exists(name)) {
            CommandInterface command = getExecutor(name);
            if (command instanceof TabCompleter) {
                return (TabCompleter) command;
            }
        }
        return null;
    }
}