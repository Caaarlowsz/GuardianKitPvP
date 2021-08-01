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
            return "Boosters Main";
        }
    },
    BOOSTERS_GLOBAL{
        @Override
        public String getName() {
            return "Boosters Global";
        }
    },
    BOOSTERS_PERSONAL{
        @Override
        public String getName() {
            return "Boosters Personal";
        }
    },
    COINS{
        @Override
        public String getName() {
            return "Coins";
        }
    }
}
