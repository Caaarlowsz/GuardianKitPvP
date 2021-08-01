package dev.mruniverse.guardiankitpvp.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class ExtrasListener implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if(event.getEntity().getType().equals(EntityType.PLAYER)) {
            event.setFoodLevel(20);
        }
    }
}
