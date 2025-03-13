package me.mrstick.patternLogin.Commands.Command.PatternLogin.SubCommands;

import me.mrstick.patternLogin.Commands.CommandInterface;
import me.mrstick.patternLogin.Inventories.CraftingTable;
import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import me.mrstick.patternLogin.Utils.Strorage.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class relogin implements CommandInterface {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage(Messages.permission_error);
            return false;
        }
        // /patternlogin relgin <player>
        if (args.length < 2) {
            sender.sendMessage(Messages.pattern_relogin_usage);
            return false;
        }

        String arg = args[1];
        Player player = Bukkit.getPlayer(arg);

        if (player == null) {
            sender.sendMessage(Messages.unknown_player);
            return false;
        }

        if (Configurations.kick_on_logout) { // If kick on logout enabled
            player.kick(Component.text(Messages.logged_out_by_admin_kick_msg));
            sender.sendMessage(Messages.logged_out_success);
            return true;
        }

        Logins.UnLogin(player.getUniqueId());
        player.openInventory(new CraftingTable().craftingTable);
        player.sendMessage(Messages.logged_out_by_admin);

        sender.sendMessage(Messages.logged_out_success);

        return false;
    }
}
