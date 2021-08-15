package dev.mruniverse.guardiankitpvp.kits;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.KitItem;
import dev.mruniverse.guardiankitpvp.enums.KitType;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitInfo;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitMenu;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitMenuBuilder implements KitMenu {

    private final GuardianKitPvP plugin;
    private final KitType mode;
    private final Player player;

    private String name = ChatColor.translateAlternateColorCodes('&',"&8Kits: Normal");

    private int rows = 45;

    private Inventory chestInventory;

    public KitMenuBuilder(GuardianKitPvP main, KitType kitMode, Player player) {
        plugin = main;
        mode = kitMode;
        this.player = player;
        createInv();
    }

    @Override
    public void createInv() {
        name = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.MENUS).getString("kits.title","&8Kits: %type%");

        name = ChatColor.translateAlternateColorCodes('&',name.replace("%type%",mode.getName(false)));

        rows = getRows(plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.MENUS).getInt("kits.rows"));

        chestInventory = plugin.getServer().createInventory(null,rows,name);
    }

    @Override
    public void setName(String newName) {
        name = newName;

        name = ChatColor.translateAlternateColorCodes('&',name.replace("%type%",mode.getName(false)));

        chestInventory = plugin.getServer().createInventory(null,rows,name);
    }

    @Override
    public void pasteItems() {
        chestInventory.clear();
        int slot = 0;
        int maxSlot = chestInventory.getSize();
        PlayerManager info = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
        for(Map.Entry<String, KitInfo> kitData : plugin.getKitPvP().getKitLoader().getKits(mode).entrySet()) {
            if(slot != maxSlot) {
                if(info.getKits().contains(kitData.getValue().getID())) {
                    ItemStack kitItem = kitData.getValue().getItem(KitItem.UNLOCKED_ITEM);
                    chestInventory.setItem(kitData.getValue().getKitMenuSlot(), kitItem);
                } else {
                    ItemStack kitItem = kitData.getValue().getItem(KitItem.LOCKED_ITEM);
                    chestInventory.setItem(kitData.getValue().getKitMenuSlot(), kitItem);
                }
            }
            slot++;
        }
    }

    @Override
    public int getRows(int small) {
        if(small == 1) return 9;
        if(small == 2) return 18;
        if(small == 3) return 27;
        if(small == 4) return 36;
        if(small == 5) return 45;
        return 54;
    }

    @Override
    public String getKitName(String name, KitInfo kitInfo) {
        name = name.replace("%kit_name%",kitInfo.getName())
                .replace("%name%",kitInfo.getName())
                .replace("%kit_price%",kitInfo.getPrice() + "")
                .replace("%price%",kitInfo.getPrice() + "");
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public HashMap<ItemStack, String> getItems() {
        HashMap<ItemStack, String> kits = new HashMap<>();
        PlayerManager info = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
        for(Map.Entry<String, KitInfo> kitData : plugin.getKitPvP().getKitLoader().getKits(mode).entrySet()) {
            if(info.getKits().contains(kitData.getValue().getID())) {
                ItemStack kitItem = kitData.getValue().getItem(KitItem.UNLOCKED_ITEM);
                kits.put(kitItem,kitData.getKey());
            } else {
                ItemStack kitItem = kitData.getValue().getItem(KitItem.LOCKED_ITEM);
                kits.put(kitItem,kitData.getKey());
            }
        }
        return kits;
    }

    @Override
    public List<String> getLore(List<String> lore, KitInfo kitInfo) {
        List<String> newLore = new ArrayList<>();
        for(String line : lore) {
            newLore.add(line.replace("%kit_name%",kitInfo.getName()).replace("%price%",kitInfo.getPrice() + ""));
        }
        return newLore;
    }

    @Override
    public Inventory getInventory() {
        pasteItems();
        return chestInventory;
    }
}
