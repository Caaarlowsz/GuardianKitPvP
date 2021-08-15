package dev.mruniverse.guardiankitpvp.enums;

public enum KitType {
    NORMAL,
    TOURNAMENT,
    EVENTS;

    public String getPath(PathType type) {
        switch (this) {
            default:
            case NORMAL:
                return "kits.normal" + type.getID();
            case TOURNAMENT:
                return "kits.tournament" + type.getID();
            case EVENTS:
                return "kits.events" + type.getID();
        }
    }

    public String getPath(PathType type,String key) {
        switch (this) {
            default:
            case NORMAL:
                return "kits.normal." + key + type.getID();
            case TOURNAMENT:
                return "kits.tournament." + key + type.getID();
            case EVENTS:
                return "kits.events." + key + type.getID();
        }
    }
}
