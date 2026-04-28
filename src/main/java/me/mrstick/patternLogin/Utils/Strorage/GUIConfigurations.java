package me.mrstick.patternLogin.Utils.Strorage;

import me.mrstick.patternLogin.Utils.PluginManager;
import org.bukkit.Sound;

import java.util.List;

public class GUIConfigurations {

    public static final String main_title = PluginManager.PatternGUIChanger.SimpleColorGrade("GUI.titles.default");
    public static final String new_title = PluginManager.PatternGUIChanger.SimpleColorGrade("GUI.titles.on_new");

    public static final boolean is_sound_enabled = PluginManager.PatternGUIConfig.getBoolean("GUI.sounds.enable");

    public static final Sound on_success = Sound.valueOf(PluginManager.PatternGUIConfig.getString("GUI.sounds.on-success"));
    public static final Sound on_select = Sound.valueOf(PluginManager.PatternGUIConfig.getString("GUI.sounds.on-select"));
    public static final Sound on_wrong = Sound.valueOf(PluginManager.PatternGUIConfig.getString("GUI.sounds.on-wrong"));
    public static final Sound on_change = Sound.valueOf(PluginManager.PatternGUIConfig.getString("GUI.sounds.on-change"));

    /// SUBMITTER

    public static final String default_submit_name = PluginManager.PatternGUIChanger.SimpleColorGrade("GUI.status.default.name");
    public static final List<String> default_submit_lore = PluginManager.PatternGUIChanger.SimpleListColorGrade("GUI.status.default.lore");

    public static final String wrong_submit_name = PluginManager.PatternGUIChanger.SimpleColorGrade("GUI.status.wrong.name");
    public static final List<String> wrong_submit_lore = PluginManager.PatternGUIChanger.SimpleListColorGrade("GUI.status.wrong.lore");

}
