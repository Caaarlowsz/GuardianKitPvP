package dev.mruniverse.guardiankitpvp.kits;

import dev.mruniverse.guardiankitpvp.interfaces.kits.KitInfo;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitMenu;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class KitMenuBuilder implements KitMenu {
    @Override
    public void createInv() {

    }

    @Override
    public void setName(String newName) {

    }

    @Override
    public void pasteItems() {

    }

    @Override
    public int getRows(int small) {
        return 0;
    }

    @Override
    public String getKitName(String name, KitInfo kitInfo) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public HashMap<ItemStack, String> getItems() {
        return null;
    }

    @Override
    public List<String> getLore(List<String> lore, KitInfo kitInfo) {
        return null;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
