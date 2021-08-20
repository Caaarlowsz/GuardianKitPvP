package dev.mruniverse.guardiankitpvp.interfaces.listeners;

import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianInventory;
import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianMenu;
import dev.mruniverse.guardianlib.core.menus.interfaces.Menus;
import org.bukkit.Location;

public interface ListenerController {

    void loadListeners();

    void reloadListeners();

    void setMapLocation(Location location);

    GuardianInventory getNormalInventory();

    GuardianMenu getMenu(Menus Menu);

    Location getMapLocation();
}
