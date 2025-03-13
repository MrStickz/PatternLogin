package me.mrstick.patternLogin.Scripts;

import me.mrstick.patternLogin.PatternLogin;
import org.bukkit.Bukkit;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataCreation {

    public static String dataFolder = "PatternLogin";

    public static void create() {
        File folder = new File("plugins/" + dataFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Creates and updates Config.yml
        File config = new File(folder, "config.yml");
        if (!config.exists()) {
            createFile(config);
            copyDefaultConfig(config, "config.yml");
        } else {
            updateConfig(config, "config.yml");
        }

        // Creates and updates Messages.yml
        File messages = new File(folder, "messages.yml");
        if (!messages.exists()) {
            createFile(messages);
            copyDefaultConfig(messages, "messages.yml");
        } else {
            updateConfig(messages, "messages.yml");
        }


        // Create data folder and logins.json file
        File dataFolder = new File(folder, "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File logins = new File(dataFolder, "logins.json");
        if (!logins.exists()) {
            createFile(logins);
        }

        File GUIFolder = new File(folder, "GUIs");
        if (!GUIFolder.exists()) {
            GUIFolder.mkdirs();
        }

        File craftingGUI = new File(GUIFolder, "PatternGUI.yml");
        if (!craftingGUI.exists()) {
            createFile(craftingGUI);
            copyDefaultConfig(craftingGUI, "GUIs/Patterns.yml");
        }
    }

    private static void copyDefaultConfig(File configFile, String name) {
        try (InputStream inputStream = DataCreation.class.getClassLoader().getResourceAsStream(name)) {
            if (inputStream != null) {
                Files.copy(inputStream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateConfig(File file, String resourceName) {
        try {
            // Load existing config as text
            String existingConfig = new String(Files.readAllBytes(file.toPath()));
            String defaultConfig = loadDefaultConfig(resourceName);

            Map<String, String> existingValues = extractValues(existingConfig);
            Map<String, String> defaultValues = extractValues(defaultConfig);

            boolean updated = false;

            StringBuilder newConfig = new StringBuilder();

            for (String line : defaultConfig.split("\n")) {
                String key = extractKey(line);
                if (key != null) {
                    if (existingValues.containsKey(key)) {
                        newConfig.append(line.replace(defaultValues.get(key), existingValues.get(key))).append("\n");
                    } else {
                        newConfig.append(line).append("\n");
                        updated = true;
                    }
                } else {
                    newConfig.append(line).append("\n");
                }
            }

            if (updated) {
                Files.write(file.toPath(), newConfig.toString().getBytes());
                Bukkit.getConsoleSender().sendMessage(PatternLogin.prefix+"§e Updated configurations...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String loadDefaultConfig(String resourceName) {
        try (InputStream inputStream = DataCreation.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream != null) {
                return new String(inputStream.readAllBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static Map<String, String> extractValues(String config) {
        Map<String, String> values = new LinkedHashMap<>();
        for (String line : config.split("\n")) {
            String key = extractKey(line);
            if (key != null) {
                String value = extractValue(line);
                values.put(key, value);
            }
        }
        return values;
    }

    private static String extractKey(String line) {
        line = line.trim();
        if (line.contains(":")) {
            return line.split(":")[0].trim();
        }
        return null;
    }

    private static String extractValue(String line) {
        line = line.trim();
        if (line.contains(":")) {
            return line.split(":", 2)[1].trim();
        }
        return null;
    }
}
