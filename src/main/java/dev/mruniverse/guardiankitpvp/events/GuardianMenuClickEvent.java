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

    private final Enum<? extends GuardianItems> currentIdentifier;

    private final Enum<? extends Menus> currentMenu;

    public GuardianMenuClickEvent(Player player, Enum<? extends GuardianItems> currentIdentifier, Enum<? extends Menus> currentMenu) {
        this.player = player;
        this.currentIdentifier = currentIdentifier;
        this.currentMenu = currentMenu;
    }

    public Player getPlayer() {
        return player;
    }

    public Enum<? extends GuardianItems> getIdentifier() {
        return currentIdentifier;
    }

    public Enum<? extends Menus> getMenu() {
        return currentMenu;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
