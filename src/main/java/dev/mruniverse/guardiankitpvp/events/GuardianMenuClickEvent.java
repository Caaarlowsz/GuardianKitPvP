package dev.mruniverse.guardiankitpvp.events;

import dev.mruniverse.guardiankitpvp.interfaces.extras.GuardianItems;
import dev.mruniverse.guardiankitpvp.interfaces.extras.Menus;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GuardianMenuClickEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final Player player;

    private final GuardianItems currentIdentifier;

    private final Menus currentMenu;

    public GuardianMenuClickEvent(Player player, GuardianItems currentIdentifier, Menus currentMenu) {
        this.player = player;
        this.currentIdentifier = currentIdentifier;
        this.currentMenu = currentMenu;
    }

    public Player getPlayer() {
        return player;
    }

    public GuardianItems getIdentifier() {
        return currentIdentifier;
    }

    public Menus getMenu() {
        return currentMenu;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
