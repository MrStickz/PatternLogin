package me.mrstick.patternLogin;

import mc.obliviate.inventory.InventoryAPI;
import org.bstats.bukkit.Metrics;
import me.mrstick.patternLogin.Commands.Command.PatternLogin.Handler;
import me.mrstick.patternLogin.Scripts.DataCreation;
import me.mrstick.patternLogin.Utils.APIs.PlaceholderAPI;
import me.mrstick.patternLogin.Utils.ExtensionManager;
import me.mrstick.patternLogin.Utils.PluginManager;
import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PatternLogin extends JavaPlugin {

    private static JavaPlugin plugin;
    private static int matricId = 30994;

    @Override
    public void onLoad() {
        new DataCreation(this).create();
        PluginManager.Initialize();
    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getConsoleSender().sendMessage(Configurations.prefix_big);

        // Registering APIs
        new InventoryAPI(this).init();
        PlaceholderAPI.Setup();
        new Metrics(this, matricId);

        // Registering events & extensions
        PluginManager.registerEvents(this);
        ExtensionManager.Register(this);

        getCommand("patternlogin").setExecutor(new Handler());

        Bukkit.getConsoleSender().sendMessage(Configurations.prefix+"§a Ready to secure!");
    }

    public static JavaPlugin instance() {return plugin;}

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Configurations.prefix+"§c Ba-byeeeee!");
    }
}
