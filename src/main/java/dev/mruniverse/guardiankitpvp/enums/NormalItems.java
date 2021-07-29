package dev.mruniverse.guardiankitpvp.enums;

import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianItems;

public enum NormalItems implements GuardianItems {
    KITS {
        @Override
        public String getID() {
            return "kits";
        }
        @Override
        public String getPath() {
            return "items.kit-selector.";
        }
    },
    SHOP {
        @Override
        public String getID() {
            return "coins";
        }
        @Override
        public String getPath() {
            return "items.shop.";
        }
    },
    KITUNLOCKER{
        @Override
        public String getID() {
            return "unlocker";
        }
        @Override
        public String getPath() {
            return "items.kit-unlocker.";
        }
    }
}
