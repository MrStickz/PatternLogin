package me.mrstick.patternLogin.Utils.LoginManagers;

import me.mrstick.patternLogin.Utils.PluginManager;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PatternManager {

    public static void Register(UUID uuid, List<Integer> pattern) {
        PluginManager.LoginWriter.Add(uuid.toString(), pattern);
    }

    public static void UnRegister(UUID uuid) {
        PluginManager.LoginWriter.Remove(uuid.toString());
    }

    public static int Login(UUID uuid, List<Integer> pattern) {
        Object data = PluginManager.LoginWriter.Get(uuid.toString());
        if (data == null) return 404;

        List<Integer> storedPattern = ((List<?>) data).stream()
                .map(obj -> Integer.valueOf(obj.toString()))
                .collect(Collectors.toList());

        if (storedPattern.equals(pattern)) {
            return 200;
        }
        return 404;
    }



    // SEP

    public static boolean isRegistered(UUID uuid) {
        Object data = PluginManager.LoginWriter.Get(uuid.toString());
        return data != null;
    }

}
