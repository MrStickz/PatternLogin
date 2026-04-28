package me.mrstick.patternLogin.Utils.Strorage;

import me.mrstick.patternLogin.Utils.PluginManager;

public class Configurations {

    /// Plugin
    public static double version = 0.6;

    public static String prefix = "§6[§ePatternLogin§6]";
    public static final String prefix_big = """
                    \n§6[§ePatternLogin§6]: §7====================================
                    §6[§ePatternLogin§6]: §7|    §ePatternLogin§7                  |
                    §6[§ePatternLogin§6]: §7|    §7Currently running on v"""
                                + version + "     |" + """
                    \n§6[§ePatternLogin§6]: §7====================================
                    """;

    public static final int kick_after_max_tries = PluginManager.GetConfigReader().getInt("kick-after-max-tries");
    public static final boolean kick_on_logout = PluginManager.GetConfigReader().getBoolean("kick-on-logout");

    /// Extension: Cords Security
    public static final boolean isCordsSecurity_enabled = PluginManager.GetConfigReader().getBoolean("cords-security.enable");
    public static final String security_world_name = PluginManager.GetConfigReader().getString("cords-security.world.name");
    public static final double security_cord_x = PluginManager.GetConfigReader().getDouble("cords-security.world.x");
    public static final double security_cord_y = PluginManager.GetConfigReader().getDouble("cords-security.world.y");
    public static final double security_cord_z = PluginManager.GetConfigReader().getDouble("cords-security.world.z");

    /// Extension: Sessions
    public static final boolean isSession_enabled = PluginManager.GetConfigReader().getBoolean("sessions.enable");
    public static final Integer session_time = PluginManager.GetConfigReader().getInt("sessions.session-time");

}
