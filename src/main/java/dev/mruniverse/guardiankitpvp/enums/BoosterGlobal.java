package dev.mruniverse.guardiankitpvp.enums;

import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianItems;

public enum BoosterGlobal implements GuardianItems {
    FIRST {
        @Override
        public String getID() {
            return "firstGlobal";
        }
        @Override
        public String getPath() {
            return "boosters-global.items.first.";
        }
    },
    SECOND {
        @Override
        public String getID() {
            return "secondGlobal";
        }
        @Override
        public String getPath() {
            return "boosters-global.items.second.";
        }
    },
    THREE{
        @Override
        public String getID() {
            return "threeGlobal";
        }
        @Override
        public String getPath() {
            return "boosters-global.items.three.";
        }
    }
}
