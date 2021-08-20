package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.*;
import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianInventory;
import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianMenu;
import dev.mruniverse.guardiankitpvp.interfaces.listeners.ListenerController;
import dev.mruniverse.guardiankitpvp.utils.ExtraUtils;
import dev.mruniverse.guardianlib.core.GuardianLIB;
import dev.mruniverse.guardianlib.core.menus.gui.GuardianMenuBuilder;
import dev.mruniverse.guardianlib.core.menus.interfaces.Menus;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

public class ListenerControllerBuilder implements ListenerController {
    private final GuardianKitPvP plugin;

    private final ChatListener chatListener;

    private final DamageListener damageListener;

    private final JoinListener joinListener;

    private Location location;

    private GuardianMenu shopMenu;

    private GuardianMenu boostersMain;

    private GuardianMenu boostersPersonal;

    private GuardianMenu boostersGlobal;

    public ListenerControllerBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
        location = GuardianLIB.getControl().getUtils().getLocationFromString("notSet");
        chatListener = new ChatListener(plugin);
        damageListener = new DamageListener(plugin);
        joinListener = new JoinListener(plugin);
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

        boostersMain = new GuardianMenuBuilder().setMenu(GMenus.BOOSTERS)
                .setTitle(fileConfiguration.getString("boosters-main.title","&8Boosters"))
                .setRows(fileConfiguration.getInt("boosters-main.rows",3))
                .setClickCancellable(true)
                .setInventoryOwner(null)
                .createMenu()
                .setItems(fileConfiguration, ExtraUtils.getEnums(BoostersMain.class));
        boostersMain.register(plugin);
        boostersMain.updateItems();

        boostersPersonal = new GuardianMenuBuilder().setMenu(GMenus.BOOSTERS_PERSONAL)
                .setTitle(fileConfiguration.getString("boosters-personal.title","&8Personal Boosters"))
                .setRows(fileConfiguration.getInt("boosters-personal.rows",3))
                .setClickCancellable(true)
                .setInventoryOwner(null)
                .createMenu()
                .setItems(fileConfiguration, ExtraUtils.getEnums(BoosterPersonal.class));
        boostersPersonal.register(plugin);
        boostersPersonal.updateItems();

        boostersGlobal = new GuardianMenuBuilder().setMenu(GMenus.BOOSTERS_GLOBAL)
                .setTitle(fileConfiguration.getString("boosters-global.title","&8Global Boosters"))
                .setRows(fileConfiguration.getInt("boosters-global.rows",3))
                .setClickCancellable(true)
                .setInventoryOwner(null)
                .createMenu()
                .setItems(fileConfiguration, ExtraUtils.getEnums(BoosterPersonal.class));
        boostersGlobal.register(plugin);
        boostersGlobal.updateItems();
    }

    @Override
    public GuardianMenu getMenu(Menus menus) {
        if(menus == GMenus.BOOSTERS) return boostersMain;
        if(menus == GMenus.BOOSTERS_PERSONAL) return boostersPersonal;
        if(menus == GMenus.BOOSTERS_GLOBAL) return boostersGlobal;
        return shopMenu;
    }

    @Override
    public void loadListeners() {
        PluginManager manager = plugin.getServer().getPluginManager();
        manager.registerEvents(joinListener,plugin);
        manager.registerEvents(chatListener,plugin);
        manager.registerEvents(damageListener,plugin);
        manager.registerEvents(new DeathListener(plugin),plugin);
        manager.registerEvents(new InteractListener(plugin),plugin);
        manager.registerEvents(new ExtrasListener(),plugin);
        manager.registerEvents(new QuitListener(plugin),plugin);
    }

    @Override
    public void reloadListeners() {
        shopMenu.updateItems();
        chatListener.update();
        damageListener.update();
        /*
         * SOMETHING WILL BE ADDED HERE IN THE NEXT SUMMER
         */
    }

    @Override
    public GuardianInventory getNormalInventory() {
        return joinListener.getInventory();
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
