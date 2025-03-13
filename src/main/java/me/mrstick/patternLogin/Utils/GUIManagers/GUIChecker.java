package me.mrstick.patternLogin.Utils.GUIManagers;

import org.bukkit.inventory.Inventory;

public class GUIChecker {

    public static boolean isPatternInventory(Inventory inventory) {
        return inventory != null && inventory.getHolder() instanceof GUIHolders;
    }

}
