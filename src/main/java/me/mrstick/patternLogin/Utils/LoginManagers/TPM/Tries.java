package me.mrstick.patternLogin.Utils.LoginManagers.TPM;

import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import me.mrstick.patternLogin.Utils.Strorage.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Tries {

    private static final Map<UUID, Integer> triesMap = new HashMap<>();

    public static void AddTry(Player p) {
        UUID uuid = p.getUniqueId();
        if (!triesMap.containsKey(uuid)) {
            triesMap.put(uuid, 1);
            return;
        }
        triesMap.replace(uuid, triesMap.get(uuid)+1);

        if (triesMap.get(uuid) >= Configurations.kick_after_max_tries) {
            p.kick(Component.text(Messages.max_tries_reached));
        }
    }

    public static void ClearTry(UUID uuid) {
        if (!triesMap.containsKey(uuid)) return;
        triesMap.remove(uuid);
    }

    public static Integer GetTry(UUID uuid) {
        if (!triesMap.containsKey(uuid)) return 0;
        return triesMap.get(uuid);
    }

}
