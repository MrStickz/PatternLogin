package me.mrstick.patternLogin.Scripts;

import me.mrstick.patternLogin.PatternLogin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DataCreation {

    private final PatternLogin plugin;

    public DataCreation(PatternLogin plugin) {
        this.plugin = plugin;
    }

    public void create() {
        plugin.getDataFolder().mkdirs();

        setupConfig("config.yml");
        setupConfig("messages.yml");

        // GUI configs
        File guiFolder = new File(plugin.getDataFolder(), "GUIs");
        if (!guiFolder.exists()) guiFolder.mkdirs();

        setupConfig("GUIs/Patterns.yml");

        File dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdirs();

        File logins = new File(dataFolder, "logins.json");
        try {
            if (!logins.exists()) logins.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupConfig(String path) {
        File file = new File(plugin.getDataFolder(), path);

        if (!file.exists()) {
            plugin.saveResource(path, false);
            return;
        }

        FileConfiguration current = YamlConfiguration.loadConfiguration(file);
        FileConfiguration defaults = YamlConfiguration.loadConfiguration(
                new InputStreamReader(
                        plugin.getResource(path),
                        StandardCharsets.UTF_8
                )
        );

        current.setDefaults(defaults);
        current.options().copyDefaults(true);

        try {
            current.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}