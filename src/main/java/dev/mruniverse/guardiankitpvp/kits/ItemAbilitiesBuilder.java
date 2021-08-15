package dev.mruniverse.guardiankitpvp.kits;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import dev.mruniverse.guardiankitpvp.interfaces.abilities.Ability;
import dev.mruniverse.guardiankitpvp.interfaces.kits.ItemAbilities;
import dev.mruniverse.guardiankitpvp.interfaces.storage.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemAbilitiesBuilder implements ItemAbilities, Listener {
    private final GuardianKitPvP plugin;

    private final HashMap<ItemStack,List<String>> items = new HashMap<>();

    private final HashMap<String,Ability> abilitiesUsingName = new HashMap<>();

    private final List<Ability> abilities = new ArrayList<>();

    private int delay;

    public ItemAbilitiesBuilder(GuardianKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<Ability> getAbilities() {
        return abilities;
    }

    @Override
    public HashMap<ItemStack, List<String>> getItems() {
        return items;
    }

    @Override
    public ItemAbilities registerAbility(Ability ability) {
        abilities.add(ability);
        plugin.getLogs().info("&aABILITY-BUILDER | &fAbility " + ability.getName() + " registered!");
        abilitiesUsingName.put(ability.getName(),ability);
        return this;
    }

    @Override
    public void finishRegister() {
        if(abilities.size() == 1) {
            plugin.getLogs().info("&aABILITY-BUILDER | &fNow the plugin has" + abilities.size() + " ability loaded");
            return;
        }
        plugin.getLogs().info("&aABILITY-BUILDER | &fNow the plugin has" + abilities.size() + " abilities loaded");
        delay = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.ABILITIES).getInt("abilities.delay");
        plugin.getLogs().info("&aABILITY-BUILDER | &fThe delay for abilities is " + delay + " second(s).");
    }

    @Override
    public void add(ItemStack item, List<String> abilities) {
        items.put(item,abilities);
    }

    @Override
    public void remove(ItemStack item) {
        items.remove(item);
    }

    @Override
    public void update() {
        plugin.getLogs().info("&aABILITY-BUILDER | &fAbilities now has been refresh.");
        delay = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.ABILITIES).getInt("abilities.delay");
        plugin.getLogs().info("&aABILITY-BUILDER | &fThe delay for abilities is " + delay + " second(s).");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAbilityUsage(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getItem() != null) {

            ItemStack item = event.getItem();

            if (item == null) return;
            if (item.getItemMeta() == null) return;

            if(items.containsKey(item)) {
                PlayerManager manager = plugin.getKitPvP().getPlayers().getUser(player.getUniqueId());
                for(Map.Entry<String, Ability> entry : abilitiesUsingName.entrySet()) {
                    if(items.get(item).contains(entry.getKey())) {
                        entry.getValue().execute(player,manager);
                    }
                }

            }
        }

    }
}
