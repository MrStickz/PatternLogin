package me.mrstick.patternLogin.Utils.Strorage;

import org.bukkit.entity.Player;

public class Sounds {

    /**
     Easy Sounds
     **/

    public static void playNegativeSound(Player player) {
        if (GUIConfigurations.is_sound_enabled) {
            player.playSound(player.getLocation(), GUIConfigurations.on_wrong, 1.0f, 1.0f);
        }
    }

    public static void playClickSound(Player player) {
        if (GUIConfigurations.is_sound_enabled) {
            player.playSound(player.getLocation(), GUIConfigurations.on_select, 1.0f, 1.0f);
        }
    }

    public static void playSuccessSound(Player player) {
        if (GUIConfigurations.is_sound_enabled) {
            player.playSound(player.getLocation(), GUIConfigurations.on_success, 1.0f, 1.0f);
        }
    }

    public static void playPswdChangeSound(Player player) {
        if (GUIConfigurations.is_sound_enabled) {
            player.playSound(player.getLocation(), GUIConfigurations.on_change, 1.0f, 1.0f);
        }
    }

}
