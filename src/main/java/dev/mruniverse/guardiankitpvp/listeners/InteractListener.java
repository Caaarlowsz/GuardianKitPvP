package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GMenus;
import dev.mruniverse.guardiankitpvp.enums.NormalItems;
import dev.mruniverse.guardiankitpvp.enums.ShopMenu;
import dev.mruniverse.guardianlib.core.events.GuardianInventoryClickEvent;
import dev.mruniverse.guardianlib.core.events.GuardianMenuClickEvent;
import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianItems;
import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianMenu;
import dev.mruniverse.guardianlib.core.menus.interfaces.Menus;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

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
                Bukkit.broadcastMessage("I'm a god");
            }
        }
    }

    @EventHandler
    public void onInventoryInteract(GuardianInventoryClickEvent event) {
        String inventoryID = event.getInventoryID();
        GuardianItems currentItem = event.getIdentifier();
        if(inventoryID.equalsIgnoreCase("normal-inventory")) {
            if(currentItem == NormalItems.SHOP) {
                if(plugin.getKitPvP() != null && plugin.getKitPvP().getListenerController() != null) {
                    GuardianMenu shopMenu = plugin.getKitPvP().getListenerController().getShopMenu();
                    Inventory inventory = shopMenu.getInventory();
                    shopMenu.pasteInventoryItems();
                    event.getPlayer().openInventory(inventory);
                }
            }
        }

    }
}
