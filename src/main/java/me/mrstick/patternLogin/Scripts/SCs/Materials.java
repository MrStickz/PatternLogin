package me.mrstick.patternLogin.Scripts.SCs;

import org.bukkit.Material;

public class Materials {

    public static Material GetMaterial(String name) {
        Material material = null;
        try {
            material = Material.valueOf(name);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return material;
    }

}
