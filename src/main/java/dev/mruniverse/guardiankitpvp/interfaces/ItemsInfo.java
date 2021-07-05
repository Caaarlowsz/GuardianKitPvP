package dev.mruniverse.guardiankitpvp.interfaces;

import org.bukkit.inventory.ItemStack;

public interface ItemsInfo {

    void setKitSelectorItem(ItemStack itemStack);

    void setShopItem(ItemStack itemStack);

    void setKitUnlocker(ItemStack itemStack);

    void setModeKitItem(ItemStack itemStack);

    void setMapVotingItem(ItemStack itemStack);

    void setExitItem(ItemStack itemStack);



    void setKitSelectorSlot(int slot);

    void setShopSlot(int slot);

    void setKitUnlockerSlot(int slot);

    void setModeKitItem(int slot);

    void setMapVotingItem(int slot);

    void setExitSlot(int slot);



    ItemStack getKitSelectorItem();

    ItemStack getShopItem();

    ItemStack getKitUnlockerItem();

    ItemStack getModeKitItem();

    ItemStack getMapVotingItem();

    ItemStack getExitItem();

    int getKitSelectorSlot();

    int getShopSlot();

    int getKitUnlockerSlot();

    int getModeKitSlot();

    int getMapVotingSlot();

    int getExitSlot();

    int getSlot(ItemStack itemStack);

    void unload();
}
