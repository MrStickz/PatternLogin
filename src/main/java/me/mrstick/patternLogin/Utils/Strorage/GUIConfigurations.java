package me.mrstick.patternLogin.Utils.Strorage;

import me.mrstick.patternLogin.Utils.PluginManager;
import org.bukkit.Sound;

public class GUIConfigurations {

    public static final boolean is_sound_enabled = PluginManager.PatternGUIConfig.getBoolean("GUI.sounds.enable");

    public static final Sound on_success = Sound.valueOf(PluginManager.PatternGUIConfig.getString("GUI.sounds.on-success"));
    public static final Sound on_select = Sound.valueOf(PluginManager.PatternGUIConfig.getString("GUI.sounds.on-select"));
    public static final Sound on_wrong = Sound.valueOf(PluginManager.PatternGUIConfig.getString("GUI.sounds.on-wrong"));

}
