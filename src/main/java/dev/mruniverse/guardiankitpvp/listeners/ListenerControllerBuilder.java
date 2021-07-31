package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GMenus;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.ShopMenu;
import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianMenu;
import dev.mruniverse.guardiankitpvp.interfaces.listeners.ListenerController;
import dev.mruniverse.guardiankitpvp.utils.ExtraUtils;
import dev.mruniverse.guardianlib.core.GuardianLIB;
import dev.mruniverse.guardianlib.core.menus.gui.GuardianMenuBuilder;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

public class ListenerControllerBuilder implements ListenerController {
    private final GuardianKitPvP plugin;

    private ChatListener chatListener;

    private Location location;
    private GuardianMenu shopMenu;

    public ListenerControllerBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
        location = GuardianLIB.getControl().getUtils().getLocationFromString("notSet");
        chatListener = new ChatListener(plugin);
        loadListeners();

        loadMenus();
    }

    public void loadMenus() {
        FileConfiguration fileConfiguration = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.MENUS);
        shopMenu = new GuardianMenuBuilder().setMenu(GMenus.SHOP)
                .setTitle(fileConfiguration.getString("shop.title","&8Shop Menu"))
                .setRows(fileConfiguration.getInt("shop.rows",4))
                .setClickCancellable(true)
                .setInventoryOwner(null)
                .createMenu()
                .setItems(fileConfiguration, ExtraUtils.getEnums(ShopMenu.class));
        shopMenu.register(plugin);
        shopMenu.updateItems();
    }

    @Override
    public GuardianMenu getShopMenu() {
        return shopMenu;
    }

    @Override
    public void loadListeners() {
        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvents(new JoinListener(plugin),plugin);
        manager.registerEvents(chatListener,plugin);
        manager.registerEvents(new InteractListener(plugin),plugin);
        manager.registerEvents(new QuitListener(plugin),plugin);
    }

    @Override
    public void reloadListeners() {
        shopMenu.updateItems();
        chatListener.update();
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
