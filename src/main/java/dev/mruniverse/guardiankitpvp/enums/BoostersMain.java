package dev.mruniverse.guardiankitpvp.enums;

import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianItems;

public enum BoostersMain implements GuardianItems {
    FIRST {
        @Override
        public String getID() {
            return "firstMain";
        }
        @Override
        public String getPath() {
            return "boosters-main.items.first.";
        }
    },
    SECOND {
        @Override
        public String getID() {
            return "secondMain";
        }
        @Override
        public String getPath() {
            return "boosters-main.items.second.";
        }
    };
}
