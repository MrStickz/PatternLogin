package me.mrstick.patternLogin.Utils;

import me.mrstick.patternLogin.Events.onJoin;
import me.mrstick.patternLogin.Events.onLoggingIn;
import me.mrstick.patternLogin.Scripts.DataChanger;
import me.mrstick.patternLogin.Scripts.DataCreation;
import me.mrstick.patternLogin.Scripts.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PluginManager {
    
    public static String folder = DataCreation.dataFolder;

    private static FileConfiguration config = null;
    private static FileConfiguration message = null;

    private static DataChanger ConfigChanger = null;
    private static DataChanger MessageChanger = null;

    public static FileConfiguration PatternGUIConfig = null;
    public static DataChanger PatternGUIChanger = null;

    public static JsonWriter LoginWriter = null;

    public static void Initialize() {
        config = YamlConfiguration.loadConfiguration(new File("plugins/"+folder+"/config.yml"));
        message = YamlConfiguration.loadConfiguration(new File("plugins/"+folder+"/messages.yml"));

        PatternGUIConfig = YamlConfiguration.loadConfiguration(new File("plugins/"+folder+"/GUIs/PatternGUI.yml"));
        PatternGUIChanger = new DataChanger(PatternGUIConfig);

        ConfigChanger = new DataChanger(config);
        MessageChanger = new DataChanger(message);

        LoginWriter = new JsonWriter("plugins/"+folder+"/data/logins.json", true);
    }


    public static FileConfiguration GetConfigReader() {return config;}
    public static FileConfiguration GetMessageReader() {return message;}

    public static DataChanger GetConfigChanger() {return ConfigChanger;}
    public static DataChanger GetMessageChanger() {return MessageChanger;}

    // For events registration
    public static void registerEvents(JavaPlugin plugin) {

        Bukkit.getPluginManager().registerEvents(new me.mrstick.patternLogin.Events.Inventories.CraftingTable(), plugin);
        Bukkit.getPluginManager().registerEvents(new onJoin(), plugin);
        Bukkit.getPluginManager().registerEvents(new onLoggingIn(), plugin);

    }

}