package me.mrstick.patternLogin.Events.Inventories;

import me.mrstick.patternLogin.PatternLogin;
import me.mrstick.patternLogin.Utils.Events.onLoginEvent;
import me.mrstick.patternLogin.Utils.Events.onRegisterEvent;
import me.mrstick.patternLogin.Utils.GUIManagers.GUIChecker;
import me.mrstick.patternLogin.Utils.LoginManagers.Logins;
import me.mrstick.patternLogin.Utils.LoginManagers.PatternManager;
import me.mrstick.patternLogin.Utils.LoginManagers.TPM.Tries;
import me.mrstick.patternLogin.Utils.Strorage.Configurations;
import me.mrstick.patternLogin.Utils.Strorage.GUIConfigurations;
import me.mrstick.patternLogin.Utils.Strorage.Messages;
import org.bukkit.Bukkit;
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
        List<Integer> pattern = GetPattern(uuid);

        // On Register
        if (!PatternManager.isRegistered(uuid)) {

            onRegisterEvent registerEvent = new onRegisterEvent(player, inv, pattern);
            Bukkit.getPluginManager().callEvent(registerEvent);
            return;

        }

        // On Login
        if (PatternManager.Login(uuid, pattern) == 200) {

            onLoginEvent loginEvent = new onLoginEvent(player, inv);
            Bukkit.getPluginManager().callEvent(loginEvent);
            return;

        }

        ClearPattern(uuid, inv);
        player.sendMessage(Messages.wrong_pattern);

        if (GUIConfigurations.is_sound_enabled) {
            player.playSound(player.getLocation(), GUIConfigurations.on_wrong, 1.0f, 1.0f);
        }

        if (Configurations.kick_after_max_tries != 0) {
            Tries.AddTry(player);
        }
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

        if (GUIConfigurations.is_sound_enabled) {
            player.playSound(player.getLocation(), GUIConfigurations.on_select, 1.0f, 1.0f);
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


    /**
     Setup for editing PatternMap<>
     **/

    public static void AddPattern(UUID p, int num, Inventory inv) {
        if (!PatternMap.containsKey(p)) {
            PatternMap.put(p, new ArrayList<>());
        }
        PatternMap.get(p).add(num);
        int numberth = GetPattern(p).size();
        ItemStack item = new ItemStack(me.mrstick.patternLogin.Inventories.CraftingTable.chosePatternItem);
        item.setAmount(numberth);
        inv.setItem(num, item);
    }

    public static List<Integer> GetPattern(UUID p) {
        if (!PatternMap.containsKey(p)) return new ArrayList<>();
        return PatternMap.get(p);
    }

    public static void RemovePattern(UUID p, int num, Inventory inv) {
        if (!PatternMap.containsKey(p)) {
            PatternMap.put(p, new ArrayList<>());
        }
        PatternMap.get(p).remove(Integer.valueOf(num));
        inv.setItem(num, new ItemStack(me.mrstick.patternLogin.Inventories.CraftingTable.defaultPatternItem));
    }

    public static void ClearPattern(UUID p, Inventory inventory) {
        List<Integer> pattern = GetPattern(p);
        for (int i : pattern) {
            inventory.setItem(i, new ItemStack(me.mrstick.patternLogin.Inventories.CraftingTable.defaultPatternItem));
        }

        if (!PatternMap.containsKey(p)) return;
        PatternMap.get(p).clear();
    }

}
