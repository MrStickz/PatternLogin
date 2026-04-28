package me.mrstick.patternLogin.Events;

import me.mrstick.patternLogin.Commands.Command.PatternLogin.SubCommands.change;
import me.mrstick.patternLogin.Inventories.CraftingTable;
import me.mrstick.patternLogin.Utils.Events.onLoginEvent;
import me.mrstick.patternLogin.Utils.Events.onRegisterEvent;
import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import me.mrstick.patternLogin.Utils.LoginManagers.PatternManager;
import me.mrstick.patternLogin.Utils.LoginManagers.TPM.Tries;
import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import me.mrstick.patternLogin.Utils.Strorage.GUIConfigurations;
import me.mrstick.patternLogin.Utils.Strorage.Messages;
import me.mrstick.patternLogin.Utils.Strorage.Sounds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

import static me.mrstick.patternLogin.Events.Inventories.CraftingTable.ClearPattern;

public class onSuccess implements Listener {

    @EventHandler
    public void onLogin(onLoginEvent e) {

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Inventory inv = e.getInventory();


        // Instead of logging in, we re-open the GUI & Un-register the player entirely
        if (change.changePass_queue.contains(uuid) && Logins.isLoggedIn(uuid)) {
            ClearPattern(uuid, inv);
            inv.close();


            Logins.UnLogin(uuid);
            PatternManager.UnRegister(uuid);

            Sounds.playPswdChangeSound(player);
            player.openInventory(new CraftingTable(GUIConfigurations.new_title).craftingTable);

            return;
        }

        Logins.Login(player);
        player.sendMessage(Messages.login_successfully);
        ClearPattern(uuid, inv);
        ClearMaxTries(uuid);

        Sounds.playSuccessSound(player);
        inv.close();

    }

    @EventHandler
    public void onRegister(onRegisterEvent e) {

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Inventory inv = e.getInventory();

        PatternManager.Register(uuid, e.getPattern());

        Logins.Login(player);

        if (change.changePass_queue.contains(uuid)) {
            player.sendMessage(Messages.pattern_changed);
            change.changePass_queue.remove(uuid);
        } else {
            player.sendMessage(Messages.registered_successfully);
        }

        ClearPattern(uuid, inv);
        ClearMaxTries(uuid);

        Sounds.playSuccessSound(player);
        inv.close();

    }

    private void ClearMaxTries(UUID uuid) {
        if (Configurations.kick_after_max_tries != 0) {
            Tries.ClearTry(uuid);
        }
    }

}
