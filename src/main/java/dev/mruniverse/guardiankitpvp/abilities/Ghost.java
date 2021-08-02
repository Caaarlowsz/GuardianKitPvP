package dev.mruniverse.guardiankitpvp.abilities;

import dev.mruniverse.guardiankitpvp.interfaces.abilities.Ability;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class Ghost implements Ability {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public Material getActivationMaterial() {
        return null;
    }

    @Override
    public EntityType getActivationProjectile() {
        return null;
    }

    @Override
    public void load(FileConfiguration paramFileConfiguration) {

    }

    @Override
    public boolean isAttackActivated() {
        return false;
    }

    @Override
    public boolean isAttackReceiveActivated() {
        return false;
    }

    @Override
    public boolean isDamageActivated() {
        return false;
    }

    @Override
    public boolean isEntityInteractionActivated() {
        return false;
    }

    @Override
    public boolean execute(Player paramPlayer, PlayerManager paramPlayerData, Event paramEvent) {
        return false;
    }
}
