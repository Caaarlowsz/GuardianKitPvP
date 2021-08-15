package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.*;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitMenu;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import dev.mruniverse.guardianlib.core.events.GuardianInventoryClickEvent;
import dev.mruniverse.guardianlib.core.events.GuardianMenuClickEvent;
import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianItems;
import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianMenu;
import dev.mruniverse.guardianlib.core.menus.interfaces.Menus;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InteractListener implements Listener {
    private final GuardianKitPvP plugin;

    public InteractListener(GuardianKitPvP guardianKitPvP) {
        this.plugin = guardianKitPvP;
    }

    @EventHandler
    public void onShopInteract(GuardianMenuClickEvent event) {
        Menus menu = event.getMenu();
        GuardianItems currentItem = event.getIdentifier();
        if(menu == GMenus.SHOP) {
            if(currentItem == ShopMenu.BOOSTERS){
                GuardianMenu boostMenu = plugin.getKitPvP().getListenerController().getMenu(GMenus.BOOSTERS);
                Inventory inventory = boostMenu.getInventory();
                boostMenu.pasteInventoryItems();
                event.getPlayer().openInventory(inventory);
            }
            return;
        }
        if(menu == GMenus.BOOSTERS) {
            if(currentItem == BoostersMain.FIRST) {
                GuardianMenu boostMenu = plugin.getKitPvP().getListenerController().getMenu(GMenus.BOOSTERS_GLOBAL);
                Inventory inventory = boostMenu.getInventory();
                boostMenu.pasteInventoryItems();
                event.getPlayer().openInventory(inventory);
                return;
            }
            if(currentItem == BoostersMain.SECOND) {
                GuardianMenu boostMenu = plugin.getKitPvP().getListenerController().getMenu(GMenus.BOOSTERS_PERSONAL);
                Inventory inventory = boostMenu.getInventory();
                boostMenu.pasteInventoryItems();
                event.getPlayer().openInventory(inventory);
            }
        }
    }

    @EventHandler
    public void onKitMenuClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        PlayerManager data = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
        if(event.getCurrentItem() == null) return;
        for(KitType type : KitType.values()) {
            if(event.getInventory().equals(data.getKitMenu(type).getInventory())) {
                HashMap<ItemStack,String> hash = data.getKitMenu(type).getItems();
                event.setCancelled(true);
                ItemStack item = event.getCurrentItem();
                if(hash.containsKey(item)) plugin.getKitPvP().getKitLoader().getToSelect(type,player,hash.get(item));
                return;
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if(event.getInventory() != player.getInventory()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getType().equals(Material.BOWL)) {
            event.getItemDrop().remove();
        } else {
            event.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryInteract(GuardianInventoryClickEvent event) {
        String inventoryID = event.getInventoryID();
        GuardianItems currentItem = event.getIdentifier();
        if(inventoryID.equalsIgnoreCase("normal-inventory")) {
            if(currentItem == NormalItems.SHOP) {
                if(plugin.getKitPvP() != null && plugin.getKitPvP().getListenerController() != null) {
                    GuardianMenu shopMenu = plugin.getKitPvP().getListenerController().getMenu(GMenus.SHOP);
                    Inventory inventory = shopMenu.getInventory();
                    shopMenu.pasteInventoryItems();
                    event.getPlayer().openInventory(inventory);
                }
                return;
            }
            if(currentItem == NormalItems.KITS) {
                Player player = event.getPlayer();
                PlayerManager info = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
                KitMenu kitMenu = info.getKitMenu(KitType.NORMAL);
                player.openInventory(kitMenu.getInventory());
            }
        }
    }
}
