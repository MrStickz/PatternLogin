package me.mrstick.patternLogin.Commands.Command.PatternLogin.SubCommands;

import me.mrstick.patternLogin.Commands.CommandInterface;
import me.mrstick.patternLogin.Inventories.CraftingTable;
import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import me.mrstick.patternLogin.Utils.Strorage.GUIConfigurations;
import me.mrstick.patternLogin.Utils.Strorage.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class change implements CommandInterface {

    public static List<UUID> changePass_queue = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!sender.hasPermission("patternlogin.change_pass")) {
            sender.sendMessage(Messages.permission_error);
            return false;
        }
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Messages.player_only_command);
            return false;
        }

        if (!Logins.isLoggedIn(p.getUniqueId())) return false;

        p.openInventory(new CraftingTable(GUIConfigurations.new_title).craftingTable);
        changePass_queue.add(p.getUniqueId());

        return false;
    }

}
