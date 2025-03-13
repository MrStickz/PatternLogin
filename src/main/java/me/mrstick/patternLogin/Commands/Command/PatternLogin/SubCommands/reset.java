package me.mrstick.patternLogin.Commands.Command.PatternLogin.SubCommands;

import me.mrstick.patternLogin.Commands.CommandInterface;
import me.mrstick.patternLogin.Inventories.CraftingTable;
import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import me.mrstick.patternLogin.Utils.LoginManagers.PatternManager;
import me.mrstick.patternLogin.Utils.Strorage.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reset implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(Messages.permission_error);
            return false;
        }
        // /patternlogin reset <player>
        if (args.length < 2) {
            sender.sendMessage(Messages.pattern_reset_usage);
            return false;
        }

        String arg = args[1];
        Player player = Bukkit.getPlayer(arg);

        if (player == null) {
            sender.sendMessage(Messages.unknown_player);
            return false;
        }

        Logins.UnLogin(player.getUniqueId());
        PatternManager.UnRegister(player.getUniqueId());
        player.openInventory(new CraftingTable().craftingTable);

        player.sendMessage(Messages.pswd_reset_by_admin);
        sender.sendMessage(Messages.pswd_reset_success);

        return false;
    }
}
