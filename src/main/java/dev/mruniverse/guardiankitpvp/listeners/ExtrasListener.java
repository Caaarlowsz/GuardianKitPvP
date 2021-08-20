package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.KitType;
import dev.mruniverse.guardiankitpvp.enums.PlayerStatus;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ExtrasListener implements Listener {

    private final GuardianKitPvP plugin;

    public ExtrasListener(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if(event.getEntity().getType().equals(EntityType.PLAYER)) {
            event.setFoodLevel(20);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerManager info = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
        if(info.getStatus() == PlayerStatus.IN_LOBBY) {
            if(info.getLocationID().equalsIgnoreCase("spawn")) {
                if(!plugin.getKitPvP().getCuboidStorage().getCurrentSpawn().isIn(player)) {
                    info.setLocationID("MapCenter");
                    plugin.giveKit(KitType.NORMAL,player);
                }
            }
        }
    }
}
