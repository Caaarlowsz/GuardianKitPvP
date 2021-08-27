package dev.mruniverse.guardiankitpvp.enums;

public enum HoloPath {
    LOCATIONS,
    LINES,
    TOP,
    FORMAT,
    BOT,
    TOGGLE;

    public String getPath() {
        switch (this) {
            default:
            case TOGGLE:
                return ".toggle";
            case BOT:
                return ".bot";
            case TOP:
                return ".top";
            case FORMAT:
                return ".format";
            case LINES:
                return ".lines";
            case LOCATIONS:
                return ".locations";
        }
    }
}
