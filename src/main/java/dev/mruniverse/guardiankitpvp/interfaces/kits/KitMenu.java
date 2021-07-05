package dev.mruniverse.guardiankitpvp.interfaces.kits;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public interface KitMenu {
    void createInv();

    void setName(String newName);

    void pasteItems();

    int getRows(int small);

    String getKitName(String name,KitInfo kitInfo);

    String getName();

    HashMap<ItemStack,String> getItems();

    List<String> getLore(List<String> lore, KitInfo kitInfo);

    Inventory getInventory();




}
