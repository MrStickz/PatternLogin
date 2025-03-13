package me.mrstick.patternLogin.Scripts;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataChanger {

    private final FileConfiguration reader;

    public DataChanger(FileConfiguration reader) {
        this.reader = reader;
    }

    private String translateGradientAndHexCodes(String message) {
        if (message == null) return null;

        Pattern gradientPattern = Pattern.compile("(&#([A-Fa-f0-9]{6}))+");
        Matcher gradientMatcher = gradientPattern.matcher(message);
        while (gradientMatcher.find()) {
            String fullMatch = gradientMatcher.group();
            String[] hexCodes = fullMatch.split("&");
            if (hexCodes.length > 1) {
                String gradientText = applyGradient(Arrays.copyOfRange(hexCodes, 1, hexCodes.length));
                message = message.replace(fullMatch, gradientText);
            }
        }

        Pattern hexPattern = Pattern.compile("(\\{#([A-Fa-f0-9]{6})}|&#([A-Fa-f0-9]{6}))");
        Matcher hexMatcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (hexMatcher.find()) {
            String hex = hexMatcher.group(2) != null ? hexMatcher.group(2) : hexMatcher.group(3);
            StringBuilder replacement = new StringBuilder("§x");
            for (char c : hex.toCharArray()) {
                replacement.append('§').append(c);
            }
            hexMatcher.appendReplacement(buffer, replacement.toString());
        }
        hexMatcher.appendTail(buffer);

        return buffer.toString().replace("&", "§");
    }

    private String applyGradient(String[] hexCodes) {
        StringBuilder gradientText = new StringBuilder();
        for (String hex : hexCodes) {
            if (hex.startsWith("#") && hex.length() == 7) {
                StringBuilder replacement = new StringBuilder("§x");
                for (char c : hex.substring(1).toCharArray()) {
                    replacement.append('§').append(c);
                }
                gradientText.append(replacement);
            }
        }
        return gradientText.toString();
    }


    public String ColorGrade(String path) {
        String message = reader.getString(path);
        if (message == null) return null;
        message = message.replace("{prefix}", reader.getString("prefix"));
        return translateGradientAndHexCodes(message);
    }

    public String SimpleColorGrade(String path) {
        String message = reader.getString(path);
        if (message == null) return null;
        return translateGradientAndHexCodes(message);
    }

    public List<String> ListColorGrade(String path) {
        List<String> processedMessages = new ArrayList<>();
        List<String> msgs = reader.getStringList(path);
        for (String msg : msgs) {
            msg = msg.replace("{prefix}", reader.getString("prefix"));
            processedMessages.add(translateGradientAndHexCodes(msg));
        }
        return processedMessages;
    }

    public List<String> SimpleListColorGrade(String path) {
        List<String> processedMessages = new ArrayList<>();
        List<String> msgs = reader.getStringList(path);
        for (String msg : msgs) {
            processedMessages.add(translateGradientAndHexCodes(msg));
        }
        return processedMessages;
    }

    public String Placeholder(String path, String placeholder, String value) {
        String message = reader.getString(path);
        if (message == null) return null;
        message = message.replace(placeholder, value);
        message = message.replace("{prefix}", reader.getString("prefix"));
        return translateGradientAndHexCodes(message);
    }

    public String SimplePlaceholder(String path, String placeholder, String value) {
        String message = reader.getString(path);
        if (message == null) return null;
        message = message.replace(placeholder, value);
        return translateGradientAndHexCodes(message);
    }

    public String MultiPlaceholder(String path, Map<String, String> placeholders) {
        String message = reader.getString(path);
        if (message == null) return null;

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }

        message = message.replace("{prefix}", reader.getString("prefix"));
        return translateGradientAndHexCodes(message);
    }

    public String SimpleMultiPlaceholder(String path, Map<String, String> placeholders) {
        String message = reader.getString(path);
        if (message == null) return null;

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }

        return translateGradientAndHexCodes(message);
    }

    public List<String> MultiListPlaceholder(String path, List<String> placeholders, Map<String, String> values) {
        List<String> processedMessages = new ArrayList<>();
        List<String> msgs = reader.getStringList(path);
        for (String msg : msgs) {
            for (String holder : placeholders) {
                msg = msg.replace(holder, values.get(holder));
            }
            msg = msg.replace("{prefix}", reader.getString("prefix"));
            processedMessages.add(translateGradientAndHexCodes(msg));
        }
        return processedMessages;
    }

    public List<String> SimpleMultiListPlaceholder(String path, List<String> placeholders, Map<String, String> values) {
        List<String> processedMessages = new ArrayList<>();
        List<String> msgs = reader.getStringList(path);
        for (String msg : msgs) {
            for (String holder : placeholders) {
                msg = msg.replace(holder, values.get(holder));
            }
            processedMessages.add(translateGradientAndHexCodes(msg));
        }
        return processedMessages;
    }

    public static List<String> StringToList(String str) {
        String[] elements = str.replaceAll("[\\[\\]']", "").split(",");
        List<String> list = new ArrayList<>();
        for (String element : elements) {
            list.add(element.trim());
        }
        return list;
    }

    public static List<String> GetKeys(FileConfiguration config, String path) {
        List<String> keysList = new ArrayList<>();
        if (config.isConfigurationSection(path)) {
            Set<String> keys = config.getConfigurationSection(path).getKeys(false);
            keysList.addAll(keys);
        }
        return keysList;
    }

    public static Map<String, String> toMap(List<String> keys, List<String> values) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }

    public static List<String> toList(String... values) {
        return new ArrayList<>(Arrays.asList(values));
    }
}
