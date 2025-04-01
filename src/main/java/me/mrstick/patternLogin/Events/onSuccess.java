package me.mrstick.patternLogin.Events;

import me.mrstick.patternLogin.Utils.Events.onLoginEvent;
import me.mrstick.patternLogin.Utils.Events.onRegisterEvent;
import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import me.mrstick.patternLogin.Utils.LoginManagers.PatternManager;
import me.mrstick.patternLogin.Utils.LoginManagers.TPM.Tries;
import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import me.mrstick.patternLogin.Utils.Strorage.GUIConfigurations;
import me.mrstick.patternLogin.Utils.Strorage.Messages;
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

        Logins.Login(player);
        player.sendMessage(Messages.login_successfully);
        ClearPattern(uuid, inv);

        if (GUIConfigurations.is_sound_enabled) {
            player.playSound(player.getLocation(), GUIConfigurations.on_success, 1.0f, 1.0f);
        }
        inv.close();

        if (Configurations.kick_after_max_tries != 0) {
            Tries.ClearTry(uuid);
        }
    }

    @EventHandler
    public void onRegister(onRegisterEvent e) {

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        Inventory inv = e.getInventory();

        PatternManager.Register(uuid, e.getPattern());

        Logins.Login(player);
        player.sendMessage(Messages.registered_successfully);

        if (GUIConfigurations.is_sound_enabled) {
            player.playSound(player.getLocation(), GUIConfigurations.on_success, 1.0f, 1.0f);
        }
        inv.close();

    }

}
