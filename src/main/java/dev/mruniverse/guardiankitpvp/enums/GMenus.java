package dev.mruniverse.guardiankitpvp.enums;

import dev.mruniverse.guardiankitpvp.interfaces.extras.Menus;

public enum GMenus implements Menus {
    SHOP,
    BOOSTERS,
    COINS;

    @Override
    public String getName() {
        switch (this) {
            default:
            case SHOP:
                return "Shop";
            case BOOSTERS:
                return "Boosters";
            case COINS:
                return "Coins";
        }
    }
}
