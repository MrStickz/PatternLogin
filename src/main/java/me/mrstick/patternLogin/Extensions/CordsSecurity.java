package me.mrstick.patternLogin.Extensions;

import me.mrstick.patternLogin.PatternLogin;
import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class CordsSecurity implements Listener {

    public static final World world = loadWorld();

    public static Location GetSecurityWorldLocation() {
        double x = Configurations.security_cord_x;
        double y = Configurations.security_cord_y;
        double z = Configurations.security_cord_z;
        return new Location(world,x,y,z);
    }

    public static World loadWorld() {
        String securityWorldName = Configurations.security_world_name;
        World world = Bukkit.getWorld(securityWorldName);
        if (world == null) {
            Bukkit.getConsoleSender().sendMessage(PatternLogin.prefix+"§c Couldn't found security world named: "+securityWorldName);
            return null;
        }
        return world;
    }

    private static final Map<Player, Location> lastLocation = new HashMap<>();

    public static void Store(Player p, Location loc) {
        lastLocation.put(p, loc);
    }

    public static void UnStore(Player p) {
        if (!lastLocation.containsKey(p)) return;
        lastLocation.remove(p);
    }

    public static void Teleport(Player p) {
        if (!lastLocation.containsKey(p)) return;
        p.teleport(lastLocation.get(p));
        UnStore(p);
    }

    // Listeners

    @EventHandler
    public void onSecurityJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        p.teleport(CordsSecurity.GetSecurityWorldLocation());
        Store(p, loc);
    }

    @EventHandler
    public void onSecurityQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (Logins.isLoggedIn(p.getUniqueId())) return;

        Teleport(p);
    }

}
