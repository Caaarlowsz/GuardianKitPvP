package dev.mruniverse.guardiankitpvp.interfaces.kits;

import dev.mruniverse.guardiankitpvp.interfaces.abilities.Ability;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public interface ItemAbilities {

    List<Ability> getAbilities();

    HashMap<ItemStack,List<String>> getItems();

    ItemAbilities registerAbility(Ability ability);

    void finishRegister();

    void add(ItemStack item,List<String> abilities);

    void remove(ItemStack item);

    void update();

}
