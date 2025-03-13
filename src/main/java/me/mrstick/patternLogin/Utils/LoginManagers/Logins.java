package me.mrstick.patternLogin.Utils.LoginManagers;

import me.mrstick.patternLogin.Extensions.CordsSecurity;
import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Logins {

    private static final List<UUID> loggedIn = new ArrayList<>();

    public static boolean isLoggedIn(UUID uuid) {
        return loggedIn.contains(uuid);
    }

    public static void Login(Player p) {
        UUID uuid = p.getUniqueId();
        if (loggedIn.contains(uuid)) return;
        loggedIn.add(uuid);

        if (Configurations.isCordsSecurity_enabled) {
            CordsSecurity.Teleport(p);
        }
    }

    public static void UnLogin(UUID uuid) {
        if (!loggedIn.contains(uuid)) return;
        loggedIn.remove(uuid);
    }

}
