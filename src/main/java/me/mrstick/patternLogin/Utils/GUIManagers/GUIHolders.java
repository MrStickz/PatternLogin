package me.mrstick.patternLogin.Utils.GUIManagers;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GUIHolders implements InventoryHolder {

    private final String id;

    public GUIHolders(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
