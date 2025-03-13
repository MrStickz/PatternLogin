package me.mrstick.patternLogin.Events.Inventories;

import me.mrstick.patternLogin.PatternLogin;
import me.mrstick.patternLogin.Utils.GUIManagers.GUIChecker;
import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import me.mrstick.patternLogin.Utils.LoginManagers.PatternManager;
import me.mrstick.patternLogin.Utils.Strorage.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class CraftingTable implements Listener {

    private static final Map<UUID, List<Integer>> PatternMap = new HashMap<>();


    @EventHandler
    public void onSubmit(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        if (!GUIChecker.isPatternInventory(inv)) return;

        ItemStack submitter = e.getCurrentItem();
        if (!isSubmitter(submitter)) return;
        e.setCancelled(true);

        Player player = (Player) e.getView().getPlayer();
        UUID uuid = player.getUniqueId();
        List<Integer> pattern = getPattern(uuid);

        // On Register
        if (!PatternManager.isRegistered(uuid)) {
            PatternManager.Register(uuid, pattern);

            Logins.Login(player);
            player.sendMessage(Messages.registered_successfully);
            inv.close();

            return;
        }

        // On Login
        if (PatternManager.Login(uuid, pattern) == 200) {
            Logins.Login(player);
            player.sendMessage(Messages.login_successfully);

            ClearPattern(uuid, inv);
            inv.close();

            return;
        }

        ClearPattern(uuid, inv);
        player.sendMessage(Messages.wrong_pattern);
    }


    @EventHandler
    public void onPatternSelect(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        if (!GUIChecker.isPatternInventory(inv)) return;

        ItemStack block = e.getCurrentItem();
        if (isSubmitter(block)) return;

        Player player = (Player) e.getView().getPlayer();
        UUID uuid = player.getUniqueId();
        int slot = e.getSlot();

        // Default block
        if (isBlock(block)) {
            e.setCancelled(true);

            AddPattern(uuid, slot, inv);
        }

        // Chosen Block
        if (isChosenBlock(block)) {
            e.setCancelled(true);

            RemovePattern(uuid, slot, inv);
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e) {
        Inventory inv = e.getInventory();

        if (!GUIChecker.isPatternInventory(inv)) return;
        if (!Logins.isLoggedIn(e.getPlayer().getUniqueId())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    e.getPlayer().openInventory(inv);
                }
            }.runTaskLater(PatternLogin.instance(), 1L);
        }
    }

    /**
     Checkers
     **/

    private static boolean isSubmitter(ItemStack item) {
        if (item == null) return false;
        if (!item.hasItemMeta()) return false;
        if (!item.getItemMeta().hasCustomModelData()) return false;
        return me.mrstick.patternLogin.Inventories.CraftingTable.submitterId == item.getItemMeta().getCustomModelData();
    }

    private static boolean isBlock(ItemStack item) {
        if (item == null) return false;
        return me.mrstick.patternLogin.Inventories.CraftingTable.defaultPatternItem.equals(item.getType());
    }

    private static boolean isChosenBlock(ItemStack item) {
        if (item == null) return false;
        return me.mrstick.patternLogin.Inventories.CraftingTable.chosePatternItem.equals(item.getType());
    }

    private static final List<Integer> emptyList = new ArrayList<>();

    public static List<Integer> getPattern(UUID p) {
        if (!PatternMap.containsKey(p)) return emptyList;
        return PatternMap.get(p);
    }

    /**
     Setup for editing PatternMap<>
     **/

    public static void AddPattern(UUID p, int num, Inventory inv) {
        if (!PatternMap.containsKey(p)) {
            PatternMap.put(p, emptyList);
        }
        PatternMap.get(p).add(num);
        inv.setItem(num, new ItemStack(me.mrstick.patternLogin.Inventories.CraftingTable.chosePatternItem));
    }

    public static void RemovePattern(UUID p, int num, Inventory inv) {
        if (!PatternMap.containsKey(p)) {
            PatternMap.put(p, emptyList);
        }
        PatternMap.get(p).remove(Integer.valueOf(num));
        inv.setItem(num, new ItemStack(me.mrstick.patternLogin.Inventories.CraftingTable.defaultPatternItem));
    }

    public static void ClearPattern(UUID p, Inventory inventory) {
        List<Integer> pattern = getPattern(p);
        for (int i : pattern) {
            inventory.setItem(i, new ItemStack(me.mrstick.patternLogin.Inventories.CraftingTable.defaultPatternItem));
        }

        if (!PatternMap.containsKey(p)) return;
        PatternMap.get(p).clear();
    }

}
