package dev.mruniverse.guardiankitpvp.enums;

public enum PathType {
    WITH,
    KIT_OPTIONS,
    ARMOR_HELMET,
    ARMOR_CHESTPLATE,
    ARMOR_LEGGINGS,
    ARMOR_BOOTS,
    LOCKED_ITEM,
    UNLOCKED_ITEM,
    ITEMS,
    WITHOUT;

    public String getID() {
        switch (this) {
            case ARMOR_CHESTPLATE:
                return ".armor.chestplate";
            case ARMOR_LEGGINGS:
                return ".armor.leggings.";
            case ARMOR_HELMET:
                return ".armor.helmet.";
            case ARMOR_BOOTS:
                return ".armor.boots.";
            case LOCKED_ITEM:
                return ".kit-options.locked-item.";
            case UNLOCKED_ITEM:
                return ".kit-options.unlocked-item.";
            case KIT_OPTIONS:
                return ".kit-options.";
            case ITEMS:
                return ".items.";
            case WITH:
                return ".";
            default:
            case WITHOUT:
                return "";
        }
    }


}
