package me.mrstick.patternLogin.Utils.APIs;

import me.mrstick.patternLogin.PatternLogin;
import me.mrstick.patternLogin.Utils.APIs.Placeholders.PLPlaceholders;
import org.bukkit.Bukkit;

public class PlaceholderAPI {

    public static void Setup() {
        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { //
            Bukkit.getConsoleSender().sendMessage(PatternLogin.prefix+"§c PlaceholderAPI not found! Placeholders will not work");
            return;
        }

        new PLPlaceholders().register();
    }

}
