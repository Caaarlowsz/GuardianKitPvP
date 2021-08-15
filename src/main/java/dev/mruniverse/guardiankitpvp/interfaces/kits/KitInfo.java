package dev.mruniverse.guardiankitpvp.interfaces.kits;

import dev.mruniverse.guardiankitpvp.enums.GuardianArmor;
import dev.mruniverse.guardiankitpvp.enums.KitItem;
import dev.mruniverse.guardiankitpvp.enums.KitType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public interface KitInfo {
    void loadKitItem();

    void loadInventory();

    void loadArmor();

    ItemStack loadPart(GuardianArmor armorPart);

    int getKitMenuSlot();

    HashMap<ItemStack, Integer> getInventoryItems();

    ItemStack getArmor(GuardianArmor armorPart);

    ItemStack getItem(KitItem kitItem);

    String getName();

    String getID();

    int getPrice();

    KitType getType();
}
