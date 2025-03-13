package me.mrstick.patternLogin.Utils.APIs.Placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.mrstick.patternLogin.PatternLogin;
import me.mrstick.patternLogin.Utils.LoginManagers.PatternManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PLPlaceholders extends PlaceholderExpansion {

    @Override
    @NotNull
    public String getAuthor() {
        return "MrStick";
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return "patternlogin";
    }

    @Override
    @NotNull
    public String getVersion() {
        return String.valueOf(PatternLogin.version);
    }


    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {

        if (params.equalsIgnoreCase("isRegistered")) {
            return String.valueOf(PatternManager.isRegistered(player.getUniqueId()));
        }

        return params;
    }
}