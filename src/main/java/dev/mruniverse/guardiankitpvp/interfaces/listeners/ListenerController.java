package dev.mruniverse.guardiankitpvp.interfaces.listeners;

import org.bukkit.Location;

public interface ListenerController {

    void loadListeners();

    void reloadListeners();

    void setMapLocation(Location location);

    Location getMapLocation();
}
