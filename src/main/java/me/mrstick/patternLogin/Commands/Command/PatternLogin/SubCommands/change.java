package me.mrstick.patternLogin.Commands.Command.PatternLogin.SubCommands;

import me.mrstick.patternLogin.Commands.CommandInterface;
import me.mrstick.patternLogin.Utils.Strorage.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class change implements CommandInterface {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(Messages.permission_error);
            return false;
        }

        // TODO: ALOT

        return false;
    }
}
