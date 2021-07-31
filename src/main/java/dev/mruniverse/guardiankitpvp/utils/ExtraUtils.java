package dev.mruniverse.guardiankitpvp.utils;

import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianItems;

public class ExtraUtils {
    public static <T extends Enum<T> & GuardianItems> GuardianItems[] getEnums(Class<T> paramClass) {
        return paramClass.getEnumConstants();
    }



}
