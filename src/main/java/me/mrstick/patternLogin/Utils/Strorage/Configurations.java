package me.mrstick.patternLogin.Utils.Strorage;

import me.mrstick.patternLogin.Utils.PluginManager;

public class Configurations {

    public static final boolean isCordsSecurity_enabled = PluginManager.GetConfigReader().getBoolean("cords-security.enable");
    public static final String security_world_name = PluginManager.GetConfigReader().getString("cords-security.world.name");
    public static final double security_cord_x = PluginManager.GetConfigReader().getDouble("cords-security.world.x");
    public static final double security_cord_y = PluginManager.GetConfigReader().getDouble("cords-security.world.y");
    public static final double security_cord_z = PluginManager.GetConfigReader().getDouble("cords-security.world.z");

    public static final int kick_after_max_tries = PluginManager.GetConfigReader().getInt("kick-after-max-tries");

    public static final boolean kick_on_logout = PluginManager.GetConfigReader().getBoolean("kick-on-logout");

}
