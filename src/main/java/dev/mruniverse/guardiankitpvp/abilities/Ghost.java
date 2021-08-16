package dev.mruniverse.guardiankitpvp.abilities;

import dev.mruniverse.guardiankitpvp.interfaces.abilities.Ability;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import dev.mruniverse.guardianlib.core.utils.Utils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Ghost implements Ability {

    private final Utils utils;

    public Ghost(Utils utils) {
        this.utils = utils;
    }

    @Override
    public String getName() {
        return "ghost";
    }

    @Override
    public void load(FileConfiguration paramFileConfiguration) {
        /*
         * here the ability configuration is loaded for now this isn't need
         */
    }

    @Override
    public void execute(Player player, PlayerManager data) {
        if(!data.isCountdown("ghostAbility")) {
            data.setCountdown("ghostAbility",30,true);
            utils.sendMessage(player,"&aYou used &bGhost &aAbility");
        }
    }
}
