package dev.mruniverse.guardiankitpvp.kits;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.*;
import dev.mruniverse.guardiankitpvp.interfaces.kits.KitInfo;
import dev.mruniverse.guardianlib.core.utils.Utils;
import dev.mruniverse.guardianlib.core.utils.xseries.XMaterial;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class KitInfoBuilder implements KitInfo {

    private final GuardianKitPvP plugin;

    private final HashMap<ItemStack,Integer> items = new HashMap<>();

    private final HashMap<KitItem,ItemStack> kitItems = new HashMap<>();

    private final String id;

    private final String name;

    private final KitType type;

    private final int slot;

    private final int price;

    public KitInfoBuilder(GuardianKitPvP plugin,KitType type,String name){
        this.plugin = plugin;
        this.type = type;
        this.name = name;

        FileConfiguration kits = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.KITS);

        String path = type.getPath(PathType.KIT_OPTIONS,name);

        this.id = kits.getString(path + "id");
        this.slot = kits.getInt(path + "slot");
        this.price = kits.getInt(path + "price");

        loadArmor();
        loadInventory();
        loadKitItem();

    }

    @Override
    public void loadKitItem() {
        kitItems.put(KitItem.LOCKED_ITEM,getItem(PathType.LOCKED_ITEM));
        kitItems.put(KitItem.UNLOCKED_ITEM,getItem(PathType.UNLOCKED_ITEM));
    }

    @Override
    public void loadInventory() {
        try {
            items.clear();

            String path = type.getPath(PathType.ITEMS,name);

            FileConfiguration items = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.KITS);
            ConfigurationSection section = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.KITS).getConfigurationSection(path);

            Utils utils = plugin.getUtils().getUtils();

            if (section == null) return;

            for (String item : section.getKeys(false)) {
                String material = items.getString(path + item + ".item");
                int amount = getAmount(items,path + item + ".amount");
                if (material == null) material = "BEDROCK";
                Optional<XMaterial> optionalXMaterial = XMaterial.matchXMaterial(material);
                if (optionalXMaterial.isPresent()) {
                    XMaterial m = optionalXMaterial.get();
                    if (m.parseMaterial() != null) {
                        String itemName = items.getString(path + item + ".name");
                        Integer slot = items.getInt(path + item + ".slot");
                        List<String> lore = items.getStringList(path + item + ".lore");
                        ItemStack itemStack = utils.getItem(m, itemName, lore);
                        itemStack.setAmount(amount);

                        if (items.get(path + item + ".enchantments") != null) {
                            List<String> enchantments = items.getStringList(path + item + ".enchantments");
                            itemStack = utils.getEnchantmentList(itemStack, enchantments,"none");
                        }
                        if (items.get(path + item + ".abilities") != null) {
                            List<String> abilities = items.getStringList(path + item + ".abilities");
                            plugin.getKitPvP().getItemAbilities().add(itemStack,abilities);
                            plugin.getLogs().info("&bKIT-BUILDER | &fItem-id [" + item + "] was registered with Abilities "+ abilities);
                        }
                        this.items.put(itemStack, slot);
                    }
                } else {
                    plugin.getLogs().error("&bKIT-BUILDER | &fItem: " + material + " doesn't exists.");
                }
            }
        }catch (Throwable throwable) {
            plugin.getLogs().error("&bKIT-BUILDER | &fCan't load inventory items of kit: " + name);
            plugin.getLogs().error(throwable);
        }
    }

    @Override
    public void loadArmor() {
        kitItems.put(KitItem.ARMOR_HELMET,loadPart(GuardianArmor.HELMET));
        kitItems.put(KitItem.ARMOR_CHESTPLATE,loadPart(GuardianArmor.CHESTPLATE));
        kitItems.put(KitItem.ARMOR_LEGGINGS,loadPart(GuardianArmor.LEGGINGS));
        kitItems.put(KitItem.ARMOR_BOOTS,loadPart(GuardianArmor.BOOTS));
    }

    @Override
    public ItemStack loadPart(GuardianArmor armorPart) {
        return getItem(armorPart.getType());
    }

    public ItemStack getItem(PathType pathType) {
        FileConfiguration items = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.KITS);
        String path = type.getPath(pathType,name);

        String material = items.getString(path + ".item");

        if(material == null) return null;
        if(material.equalsIgnoreCase("DISABLE")) return null;

        Optional<XMaterial> optionalXMaterial = XMaterial.matchXMaterial(material);
        if(!optionalXMaterial.isPresent()) return null;
        XMaterial m = optionalXMaterial.get();
        if (m.parseMaterial() == null) return null;
        String itemName = items.getString(path + ".name");
        List<String> lore = items.getStringList(path + ".lore");
        int amount = getAmount(items,path + ".amount");
        ItemStack returnItem = plugin.getUtils().getUtils().getItem(m,itemName,lore);
        returnItem.setAmount(amount);
        if(items.get(path + ".enchantments") == null) return returnItem;
        List<String> enchantments = items.getStringList(path + ".enchantments");
        return plugin.getUtils().getUtils().getEnchantmentList(returnItem, enchantments,"none");
    }

    @Override
    public int getKitMenuSlot() {
        return slot;
    }

    @Override
    public HashMap<ItemStack, Integer> getInventoryItems() {
        return items;
    }

    @Override
    public ItemStack getArmor(GuardianArmor armorPart) {
        return kitItems.get(armorPart.getItem());
    }

    @Override
    public ItemStack getItem(KitItem item) {
        return kitItems.get(item);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public KitType getType() {
        return type;
    }

    private int getAmount(FileConfiguration fileConfiguration,String path) {
        if(fileConfiguration.contains(path)) {
            return fileConfiguration.getInt(path);
        }
        return 1;
    }

}
