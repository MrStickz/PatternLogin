package me.mrstick.patternLogin.Commands.Command.PatternLogin;

import me.mrstick.patternLogin.Commands.Command.PatternLogin.SubCommands.relogin;
import me.mrstick.patternLogin.Commands.Command.PatternLogin.SubCommands.reset;
import me.mrstick.patternLogin.Commands.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class Handler extends CommandHandler {

    public Handler() {
        register("relogin", new relogin());
        register("reset", new reset());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, String[] args) {

        if (args.length == 0) {
            getExecutor("").onCommand(sender, cmd, commandLabel, args);
            return true;
        }

        if (exists(args[0])){
            getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("patternlogin")) {
            return super.onTabComplete(sender, cmd, alias, args);
        }

        return null;
    }

}