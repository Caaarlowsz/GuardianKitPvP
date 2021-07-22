package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.interfaces.listeners.ListenerController;
import dev.mruniverse.guardianlib.core.GuardianLIB;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;

public class ListenerControllerBuilder implements ListenerController {
    private final GuardianKitPvP plugin;
    private Location location;

    public ListenerControllerBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
        location = GuardianLIB.getControl().getUtils().getLocationFromString("notSet");
        loadListeners();
    }

    @Override
    public void loadListeners() {
        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvents(new JoinListener(plugin),plugin);
        manager.registerEvents(new QuitListener(plugin),plugin);
    }

    @Override
    public void reloadListeners() {
        /*
         * SOMETHING WILL BE ADDED HERE IN THE NEXT SUMMER
         */
    }

    @Override
    public void setMapLocation(Location location) {
        this.location = location;
    }

    @Override
    public Location getMapLocation() {
        return location;
    }
}
