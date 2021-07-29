package dev.mruniverse.guardiankitpvp.enums;


import dev.mruniverse.guardianlib.core.menus.interfaces.Menus;

public enum GMenus implements Menus {
    SHOP{
        @Override
        public String getName() {
            return "Shop";
        }
    },
    BOOSTERS{
        @Override
        public String getName() {
            return "Boosters";
        }
    },
    COINS{
        @Override
        public String getName() {
            return "Coins";
        }
    }
}
