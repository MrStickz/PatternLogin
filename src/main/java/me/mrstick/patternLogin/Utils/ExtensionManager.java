package me.mrstick.patternLogin.Utils;

import me.mrstick.patternLogin.Extensions.CordsSecurity;
import me.mrstick.patternLogin.Inventories.CraftingTable;
import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ExtensionManager {

    public static void Register(JavaPlugin plugin) {

        // Coordinates Security
        if (Configurations.isCordsSecurity_enabled) {
            Bukkit.getPluginManager().registerEvents(new CordsSecurity(), plugin);
        }


        for (Player p : Bukkit.getOnlinePlayers()) {
            p.openInventory(new CraftingTable().craftingTable);
        }
    }

}
