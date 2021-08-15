package dev.mruniverse.guardiankitpvp.interfaces.abilities;

import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public interface Ability {
    String getName();

    void load(FileConfiguration paramFileConfiguration);

    void execute(Player paramPlayer, PlayerManager paramPlayerData);
}
