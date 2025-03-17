package me.mrstick.patternLogin.Events;

import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class onLoggingIn implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Logins.isLoggedIn(p.getUniqueId())) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        if (Logins.isLoggedIn(p.getUniqueId())) return;
        e.setCancelled(true);
    }

}
