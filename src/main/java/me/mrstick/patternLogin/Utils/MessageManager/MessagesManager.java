package me.mrstick.patternLogin.Utils.MessageManager;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;

public class MessagesManager {

    public static String EncryptToPL(OfflinePlayer p, String message) {
        return PlaceholderAPI.setPlaceholders(p, message);
    }

}
