package me.mrstick.patternLogin;

import mc.obliviate.inventory.InventoryAPI;
import me.mrstick.patternLogin.Commands.Command.PatternLogin.Handler;
import me.mrstick.patternLogin.Scripts.DataCreation;
import me.mrstick.patternLogin.Utils.APIs.PlaceholderAPI;
import me.mrstick.patternLogin.Utils.ExtensionManager;
import me.mrstick.patternLogin.Utils.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PatternLogin extends JavaPlugin {

    public static String prefix = "§6[§ePatternLogin§6]";
    public static double version = 0.4;
    private static JavaPlugin plugin;


    @Override
    public void onLoad() {
        DataCreation.create();
        PluginManager.Initialize();
    }


    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getConsoleSender().sendMessage(prefix+"§e Enabling PatternLogin v"+version);

        // Registering APIs
        new InventoryAPI(this).init();
        PlaceholderAPI.Setup();

        // Registering events & extensions
        PluginManager.registerEvents(this);
        ExtensionManager.Register(this);

        getCommand("patternlogin").setExecutor(new Handler());

        Bukkit.getConsoleSender().sendMessage(prefix+"§a Ready to secure!");
    }

    public static JavaPlugin instance() {return plugin;}

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(prefix+"§c Ba-byeeeee!");
    }
}
