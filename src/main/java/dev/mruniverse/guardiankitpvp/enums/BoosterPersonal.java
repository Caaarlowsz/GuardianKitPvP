package dev.mruniverse.guardiankitpvp.enums;

import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianItems;

public enum BoosterPersonal implements GuardianItems {
    FIRST {
        @Override
        public String getID() {
            return "firstPersonal";
        }
        @Override
        public String getPath() {
            return "boosters-personal.items.first.";
        }
    },
    SECOND {
        @Override
        public String getID() {
            return "secondPersonal";
        }
        @Override
        public String getPath() {
            return "boosters-personal.items.second.";
        }
    },
    THREE{
        @Override
        public String getID() {
            return "threePersonal";
        }
        @Override
        public String getPath() {
            return "boosters-personal.items.three.";
        }
    }
}
