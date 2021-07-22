package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.enums.GMenus;
import dev.mruniverse.guardiankitpvp.enums.ShopMenu;
import dev.mruniverse.guardiankitpvp.events.GuardianMenuClickEvent;
import dev.mruniverse.guardiankitpvp.interfaces.extras.GuardianItems;
import dev.mruniverse.guardiankitpvp.interfaces.extras.Menus;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InteractListener implements Listener {

    /*
     * SHOP
     */
    @EventHandler
    public void onShopInteract(GuardianMenuClickEvent event) {
        Enum<? extends Menus> menu = event.getMenu();
        Enum<? extends GuardianItems> currentItem = event.getIdentifier();
        if(menu == GMenus.SHOP) {
            if(currentItem == ShopMenu.BOOSTERS){
                Bukkit.broadcastMessage("I'm a god");
            }
        }
    }
}
