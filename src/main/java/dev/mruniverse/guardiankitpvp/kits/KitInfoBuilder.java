package dev.mruniverse.guardiankitpvp.kits;

import dev.mruniverse.guardiankitpvp.enums.GuardianArmor;
import dev.mruniverse.guardiankitpvp.enums.KitType;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitInfo;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KitInfoBuilder implements KitInfo {
    @Override
    public void loadKitItem() {

    }

    @Override
    public void loadInventory() {

    }

    @Override
    public void loadArmor() {

    }

    @Override
    public ItemStack loadPart(GuardianArmor armorPart) {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public int getKitMenuSlot() {
        return 0;
    }

    @Override
    public HashMap<ItemStack, Integer> getInventoryItems() {
        return null;
    }

    @Override
    public ItemStack getArmor(GuardianArmor armorPart) {
        return null;
    }

    @Override
    public ItemStack getKitItem() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public KitType getType() {
        return null;
    }
}
