package dev.mruniverse.guardiankitpvp.enums;

public enum GuardianArmor {
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS;

    public KitItem getItem() {
        switch (this) {
            case HELMET:
                return KitItem.ARMOR_HELMET;
            case BOOTS:
                return KitItem.ARMOR_BOOTS;
            case LEGGINGS:
                return KitItem.ARMOR_LEGGINGS;
            case CHESTPLATE:
            default:
                return KitItem.ARMOR_CHESTPLATE;
        }
    }

    public PathType getType() {
        switch (this) {
            case HELMET:
                return PathType.ARMOR_HELMET;
            case BOOTS:
                return PathType.ARMOR_BOOTS;
            case LEGGINGS:
                return PathType.ARMOR_LEGGINGS;
            case CHESTPLATE:
            default:
                return PathType.ARMOR_CHESTPLATE;
        }
    }

    public String getPartName() {
        switch (this) {
            case HELMET:
                return "Helmet";
            case BOOTS:
                return "Boots";
            case LEGGINGS:
                return "Leggings";
            case CHESTPLATE:
            default:
                return "Chestplate";
        }
    }
}
