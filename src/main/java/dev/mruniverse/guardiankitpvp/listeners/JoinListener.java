package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianBoard;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.enums.NormalItems;
import dev.mruniverse.guardiankitpvp.utils.ExtraUtils;
import dev.mruniverse.guardianlib.core.menus.interfaces.GuardianInventory;
import dev.mruniverse.guardianlib.core.menus.inventory.GuardianInventoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final GuardianKitPvP plugin;
    private GuardianInventory inventory = null;

    public JoinListener(GuardianKitPvP plugin) {
        this.plugin = plugin;
        if(plugin.getKitPvP() != null) {
            if(plugin.getKitPvP().getFileStorage() != null) {
                if (plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.ITEMS) != null) {
                    inventory = new GuardianInventoryBuilder()
                            .setID("normal-inventory")
                            .setClickCancellable(true)
                            .setItems(plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.ITEMS), ExtraUtils.getEnums(NormalItems.class));
                    inventory.register(plugin);
                }
            }
        }
    }
    private void fixInventory() {
        if(plugin.getKitPvP() != null) {
            if(plugin.getKitPvP().getFileStorage() != null) {
                if (plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.ITEMS) != null) {
                    inventory = new GuardianInventoryBuilder().setID("normal-inventory");
                    inventory = inventory.setClickCancellable(true)
                            .setItems(plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.ITEMS), ExtraUtils.getEnums(NormalItems.class));
                    inventory.register(plugin);
                    plugin.getLogs().info("Normal-Inventory registered");
                }
            }
        }
    }

    @EventHandler
    public void teleport(PlayerJoinEvent event) {
        if(inventory == null) fixInventory();
        inventory.giveInventory(event.getPlayer(),true);
        if(plugin.getKitPvP().getListenerController().getMapLocation() != null) {
            try {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> event.getPlayer().teleport(plugin.getKitPvP().getListenerController().getMapLocation()), 4L);
                event.getPlayer().setGameMode(GameMode.ADVENTURE);
            } catch (Throwable throwable) {
                plugin.getLogs().error("Can't teleport " + event.getPlayer().getName() + " to the lobby!");
                plugin.getLogs().error(throwable);
            }
            return;
        }
        plugin.getLogs().error("The lobby is not set!");
    }
    @EventHandler
    public void scoreboard(PlayerJoinEvent event) {
        try {
            if(plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SCOREBOARD).getBoolean("scoreboards.normal.toggle")) {
                plugin.getKitPvP().getBoardController().setScoreboard(GuardianBoard.NORMAL,event.getPlayer());
            }
        } catch (Throwable throwable) {
            plugin.getLogs().error("Can't generate lobby scoreboard for " + event.getPlayer().getName() +"!");
            plugin.getLogs().error(throwable);
        }
    }

    @EventHandler
    public void extras(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        player.setGameMode(GameMode.ADVENTURE);
        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0.0F);
        if(!plugin.getKitPvP().getPlayers().existPlayer(player.getUniqueId())) plugin.getKitPvP().getPlayers().addPlayer(player);
    }

    public GuardianInventory getInventory(){
        return inventory;
    }

}
