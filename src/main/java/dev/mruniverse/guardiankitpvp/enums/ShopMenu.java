package dev.mruniverse.guardiankitpvp.enums;

import dev.mruniverse.guardiankitpvp.interfaces.extras.GuardianItems;

public enum ShopMenu implements GuardianItems {
    KITS,
    COINS,
    BOOSTERS;

    //String name = menu.getString(mainAction.getPath() + ".name");
    //                    String material = menu.getString(mainAction.getPath() + ".item");
    //                    if(material == null) material = "BEDROCK";
    //                    List<String> lore = menu.getStringList(mainAction.getPath() + ".lore");
    //                    int slot = menu.getInt(mainAction.getPath() + ".slot");
    //                    Optional<XMaterial> optionalXMaterial = XMaterial.matchXMaterial(material);
    //                    ItemStack item = optionalXMaterial.map(xMaterial -> GuardianLIB.getControl().getUtils().getItem(xMaterial, name, lore)).orElse(null);
    //                    gameItems.put(item, slot);
    //                    gameAction.put(item, mainAction);

    public String getPath() {
        switch (this) {
            case BOOSTERS:
                return "shop.items.boosters.";
            case COINS:
                return "shop.items.coins.";
            default:
            case KITS:
                return "shop.items.kits.";
        }
    }
}
