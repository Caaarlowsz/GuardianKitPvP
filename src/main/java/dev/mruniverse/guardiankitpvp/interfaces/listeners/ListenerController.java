package dev.mruniverse.guardiankitpvp.interfaces.listeners;

import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianMenu;
import org.bukkit.Location;

public interface ListenerController {

    void loadListeners();

    void reloadListeners();

    void setMapLocation(Location location);

    GuardianMenu getShopMenu();

    Location getMapLocation();
}
