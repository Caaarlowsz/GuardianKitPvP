package dev.mruniverse.guardiankitpvp.enums;

import dev.mruniverse.guardiankitpvp.interfaces.extras.GuardianItems;

public enum ShopMenu implements GuardianItems {
    KITS {
        @Override
        public String getID() {
            return "kits";
        }
        @Override
        public String getPath() {
            return "shop.items.kits.";
        }
    },
    COINS {
        @Override
        public String getID() {
            return "coins";
        }
        @Override
        public String getPath() {
            return "shop.items.coins.";
        }
    },
    BOOSTERS{
        @Override
        public String getID() {
            return "boosters";
        }
        @Override
        public String getPath() {
            return "shop.items.boosters.";
        }
    }
}
