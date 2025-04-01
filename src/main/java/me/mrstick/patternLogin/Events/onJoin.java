package me.mrstick.patternLogin.Events;

import me.mrstick.patternLogin.Extensions.Sessions;
import me.mrstick.patternLogin.Inventories.CraftingTable;
import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class onJoin implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Logins.isLoggedIn(p.getUniqueId())) return;
        if (Configurations.isSession_enabled && Sessions.isSessionActive(p)) return; /// CHANGED

        p.openInventory(new CraftingTable().craftingTable);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (!Logins.isLoggedIn(p.getUniqueId())) return;
        Logins.UnLogin(p.getUniqueId());
    }

}
