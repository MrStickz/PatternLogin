package me.mrstick.patternLogin.Inventories;

import me.mrstick.patternLogin.Scripts.SCs.Materials;
import me.mrstick.patternLogin.Utils.GUIManagers.GUIHolders;
import me.mrstick.patternLogin.Utils.MessageManager.MessagesManager;
import me.mrstick.patternLogin.Utils.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CraftingTable {

    public Inventory craftingTable;

    private static final String title = PluginManager.PatternGUIChanger.SimpleColorGrade("GUI.title");

    private static final ItemStack submitterItem = new ItemStack(Materials.GetMaterial(PluginManager.PatternGUIConfig.getString("GUI.status.item")));
    public static final int submitterId = 7987;

    public static final Material defaultPatternItem = Material.BLACK_STAINED_GLASS_PANE;
    public static final Material chosePatternItem = Material.LIME_STAINED_GLASS_PANE;

    public CraftingTable() {
        craftingTable = Bukkit.createInventory(new GUIHolders("pattern_inventory"), InventoryType.WORKBENCH, title);

        craftingTable.setItem(0, getStatusItem());

        for (int i=0; i<9; i++) {
            craftingTable.setItem(i+1, new ItemStack(defaultPatternItem));
        }
    }

    private static ItemStack getStatusItem() {
        ItemMeta meta = submitterItem.getItemMeta();
        meta.setDisplayName("§cEnter your Pattern");
        meta.setLore(List.of("§7Click to submit!"));
        meta.setCustomModelData(submitterId);
        submitterItem.setItemMeta(meta);
        return submitterItem;
    }

}