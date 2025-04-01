package me.mrstick.patternLogin.Extensions;

import me.mrstick.patternLogin.Extensions.ExSessions.SessionData;
import me.mrstick.patternLogin.PatternLogin;
import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import me.mrstick.patternLogin.Utils.Strorage.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sessions implements Listener {

    private static final Map<UUID, SessionData> sessions = new HashMap<>();

    public static void createSession(Player p) {
        UUID uuid = p.getUniqueId();
        if (sessions.containsKey(uuid)) return;

        SessionData session = new SessionData(p.getAddress().getAddress().getHostAddress(), Configurations.session_time);
        sessions.put(uuid, session);


        new BukkitRunnable() {
            @Override
            public void run() {
                System.out.println(sessions);
                SessionData session = sessions.get(uuid);
                if (session == null) {
                    cancel();
                    return;
                }

                session.decrementCooldown();

                if (session.isExpired()) {
                    cancel();
                    endSession(uuid);
                }
            }
        }.runTaskTimer(PatternLogin.instance(), 20L, 20L); // Run every second
    }

    public static void endSession(UUID uuid) {
        sessions.remove(uuid);
    }

    public static boolean isSessionActive(Player p) {
        SessionData session = sessions.get(p.getUniqueId());
        return session != null && !session.isExpired() && session.GetCooldown() > 0 && session.GetIp().equals(p.getAddress().getAddress().getHostAddress());
    }


    @EventHandler
    public void onSessionJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Logins.isLoggedIn(p.getUniqueId())) return;
        if (!isSessionActive(p)) return;

        Logins.Login(p);
        endSession(p.getUniqueId());
        p.sendMessage(Messages.loggedin_from_session);
    }

    @EventHandler
    public void onSessionStart(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (e.getReason() == PlayerQuitEvent.QuitReason.KICKED) return;
        if (Logins.isLoggedIn(p.getUniqueId())) return;
        createSession(p);
    }
}