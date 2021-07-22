package dev.mruniverse.guardiankitpvp.extras;

import dev.mruniverse.guardiankitpvp.interfaces.extras.GuardianItems;
import dev.mruniverse.guardiankitpvp.interfaces.extras.GuardianMenu;
import dev.mruniverse.guardiankitpvp.interfaces.extras.Menus;
import dev.mruniverse.guardiankitpvp.listeners.MenuListener;
import dev.mruniverse.guardianlib.core.GuardianLIB;
import dev.mruniverse.guardianlib.core.utils.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GuardianMenuBuilder implements GuardianMenu {
    private Enum<? extends Menus> currentMenu;

    private Class<Enum<? extends GuardianItems>> currentIdentifier;

    private int menuSize = 3;

    private String menuTitle = "Menu Builder";

    private HashMap<ItemStack,Integer> MENUS_ITEM_SLOT;

    private HashMap<ItemStack,Enum<? extends GuardianItems>> MENUS_ITEMS;

    private FileConfiguration fileConfiguration = null;

    private InventoryHolder inventoryOwner;

    private Inventory inventory;

    private boolean cancellable = true;

    @Override
    public GuardianMenu setMenu(Enum<? extends Menus> menuName) {
        this.currentMenu = menuName;
        return this;
    }

    @Override
    public GuardianMenu setClickCancellable(boolean cancellable) {
        this.cancellable = cancellable;
        return this;
    }

    @Override
    public GuardianMenu setRows(int size) {
        if(size >= 7) size = getRows(size);
        this.menuSize = size;
        return this;
    }

    private int getRows(int small) {
        if(small == 1) return 9;
        if(small == 2) return 18;
        if(small == 3) return 27;
        if(small == 4) return 36;
        if(small == 5) return 46;
        return 54;
    }

    @Override
    public void showMenu(Player player) {
        pasteInventoryItems();
        player.openInventory(inventory);
    }

    @Override
    public GuardianMenu setInventoryOwner(InventoryHolder owner) {
        this.inventoryOwner = owner;
        return this;
    }

    @Override
    public GuardianMenu setTitle(String menuTitle) {
        this.menuTitle = ChatColor.translateAlternateColorCodes('&',menuTitle);
        return this;
    }

    @Override
    public Enum<? extends Menus> getMenu() {
        return currentMenu;
    }

    @Override
    public void pasteInventoryItems() {
        inventory.clear();
        for(Map.Entry<ItemStack,Integer> data : MENUS_ITEM_SLOT.entrySet()) {
            inventory.setItem(data.getValue(),data.getKey());
        }
    }

    @Override
    public GuardianMenu setItems(FileConfiguration fileConfiguration, Class<Enum<? extends GuardianItems>> itemIdentifier) {
        this.fileConfiguration = fileConfiguration;
        this.currentIdentifier = itemIdentifier;
        return this;
    }

    @Override
    public GuardianMenu setItems(HashMap<ItemStack, Enum<? extends GuardianItems>> items, HashMap<ItemStack, Integer> itemSlot) {
        this.MENUS_ITEMS = items;
        this.MENUS_ITEM_SLOT = itemSlot;
        return this;
    }

    @Override
    public HashMap<ItemStack, Enum<? extends GuardianItems>> getItems() {
        return MENUS_ITEMS;
    }

    @Override
    public HashMap<ItemStack,Integer> getItemsSlot() {
        return MENUS_ITEM_SLOT;
    }

    @Override
    public GuardianMenu createMenu() {
        inventory = Bukkit.getServer().createInventory(inventoryOwner,menuSize,menuTitle);
        return this;
    }

    @Override
    public Class<Enum<? extends GuardianItems>> getIdentifier() {
        return currentIdentifier;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public boolean isCancellable() {
        return cancellable;
    }

    @Override
    public void register(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new MenuListener(this),plugin);
    }

    @Override
    public void updateItems() {
        updateNow();
    }

    @Override
    public void updateItems(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
        updateNow();
    }

    @Override
    public void updateItems(FileConfiguration fileConfiguration, Class<Enum<? extends GuardianItems>> itemIdentifier) {
        this.fileConfiguration = fileConfiguration;
        this.currentIdentifier = itemIdentifier;
        updateNow();
    }

    private void updateNow() {
        int slot;
        Optional<XMaterial> optional;
        ItemStack currentItem;
        MENUS_ITEMS.clear();
        MENUS_ITEM_SLOT.clear();
        for(Enum<? extends GuardianItems> item : currentIdentifier.getEnumConstants()) {
            String path = ((GuardianItems)item).getPath();
            if(fileConfiguration.getBoolean(path + "toggle")) {
                String name = fileConfiguration.getString(path + "name","Unknown Item Name");
                String material = fileConfiguration.getString(path + "item","BEDROCK");
                List<String> lore = fileConfiguration.getStringList(path + "lore");
                slot = fileConfiguration.getInt(path + "slot");
                optional = XMaterial.matchXMaterial(material);
                currentItem = optional.map(xMaterial -> GuardianLIB.getControl().getUtils().getItem(xMaterial, name, lore)).orElse(null);
                MENUS_ITEMS.put(currentItem,item);
                MENUS_ITEM_SLOT.put(currentItem,slot);
            }
        }
    }

    @Override
    public void updateItems(HashMap<ItemStack, Enum<? extends GuardianItems>> items) {
        MENUS_ITEMS.clear();
        MENUS_ITEMS = items;
    }

    @Override
    public void updateItems(HashMap<ItemStack, Enum<? extends GuardianItems>> items, HashMap<ItemStack, Integer> itemSlot) {
        MENUS_ITEMS.clear();
        MENUS_ITEM_SLOT.clear();

        MENUS_ITEMS = items;
        MENUS_ITEM_SLOT = itemSlot;
    }
}
