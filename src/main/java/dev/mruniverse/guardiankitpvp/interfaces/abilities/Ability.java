package dev.mruniverse.guardiankitpvp.interfaces.abilities;

import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public interface Ability {
    String getName();

    Material getActivationMaterial();

    EntityType getActivationProjectile();

    void load(FileConfiguration paramFileConfiguration);

    boolean isAttackActivated();

    boolean isAttackReceiveActivated();

    boolean isDamageActivated();

    boolean isEntityInteractionActivated();

    boolean execute(Player paramPlayer, PlayerManager paramPlayerData, Event paramEvent);
}
