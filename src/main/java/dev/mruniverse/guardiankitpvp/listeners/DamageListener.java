package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    private final GuardianKitPvP plugin;
    //messages:
    //  normal:
    //    deathMessages:
    //      pvp: '&7%victim% was killed by %attacker%'
    //      void: '&7%victim% was searching a diamond.'
    //      lava: '&7%victim% was on fire!'
    //      bow: '&7%attacker% is the best with the bow vs %victim%'
    //      otherCause: '&7%victim% died'

    private String byLavaDeath;
    private String byVoidDeath;
    private String byBowDeath;
    private String byPvPDeath;
    private String byDefaultDeath;

    private String byLavaKill;
    private String byVoidKill;
    private String byBowKill;
    private String byPvPKill;
    private String byDefaultKill;

    public DamageListener(GuardianKitPvP plugin) {
        this.plugin = plugin;
        FileConfiguration messages = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.MESSAGES);
        byLavaDeath = messages.getString("messages.game.deathMessages.lava-death");
        if (byLavaDeath == null) byLavaDeath = "&7%victim% was on fire!";
        byVoidDeath = messages.getString("messages.game.deathMessages.void-death");
        if (byVoidDeath == null) byVoidDeath = "&7%victim% was searching a diamond.";
        byDefaultDeath = messages.getString("messages.game.deathMessages.otherCause-death");
        if (byDefaultDeath == null) byDefaultDeath = "&7%victim% died";
        byPvPDeath = messages.getString("messages.game.deathMessages.pvp-death");
        if (byPvPDeath == null) byDefaultDeath = "&7%victim% was killed by %attacker%";
        byBowDeath = messages.getString("messages.game.deathMessages.bow-death");
        if (byBowDeath == null) byBowDeath = "&7%victim% was shot by %attacker%";

        byLavaKill = messages.getString("messages.game.deathMessages.lava-kill");
        if (byLavaKill == null) byLavaKill = "&7%victim% was on fire!";
        byVoidKill = messages.getString("messages.game.deathMessages.void-kill");
        if (byVoidKill == null) byVoidKill = "&7%victim% was searching a diamond.";
        byDefaultKill = messages.getString("messages.game.deathMessages.otherCause-kill");
        if (byDefaultKill == null) byDefaultKill = "&7%victim% died";
        byPvPKill = messages.getString("messages.game.deathMessages.pvp-kill");
        if (byPvPKill == null) byDefaultKill = "&7%victim% was killed by %attacker%";
        byBowKill = messages.getString("messages.game.deathMessages.bow-kill");
        if (byBowKill == null) byBowKill = "&7%victim% was shot by %attacker%";
    }

    @EventHandler
    public void damage(EntityDamageEvent event) {
        if (!event.getEntity().getType().equals(EntityType.PLAYER)) return;
        Player player = (Player) event.getEntity();
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            Location MAP_LOCATION = plugin.getKitPvP().getListenerController().getMapLocation();
            if(MAP_LOCATION != null) {
                player.teleport(MAP_LOCATION);
            }
            player.setHealth(20);
            player.setFoodLevel(20);
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
            return;
        }
        if ((player.getHealth() - event.getFinalDamage()) <= 0 && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            event.setCancelled(true);
            player.getInventory().clear();
            player.setHealth(20);
            player.setFoodLevel(20);
            player.getInventory().setBoots(null);
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            String deathMessage;
            deathMessage = getDeathMessage(player, event.getCause());
            plugin.getUtils().getUtils().sendMessage(player, deathMessage);
            plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()).addDeaths();
        }
    }

    @EventHandler
    public void LobbyProjectile(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER)) {
            Player victim = (Player) event.getEntity();
            if (event.getDamager() instanceof Arrow) {
                Arrow arrow = (Arrow) event.getDamager();
                Player shooter = (Player) arrow.getShooter();
                if (!event.isCancelled()) {
                    if ((victim.getHealth() - event.getFinalDamage()) <= 0) {
                        String deathMessage;
                        if (shooter != null) {
                            deathMessage = byBowDeath.replace("%victim%", victim.getName()).replace("%attacker%", shooter.getName());
                            String killMessage = byBowKill.replace("%victim%", victim.getName()).replace("%attacker%", shooter.getName());
                            plugin.getUtils().getUtils().sendMessage(shooter,killMessage);
                            plugin.getKitPvP().getPlayers().getUser(shooter.getUniqueId()).addKills();
                        } else {
                            deathMessage = byBowDeath.replace("%victim%", victim.getName()).replace("%attacker%", "Unknown Player");
                        }
                        plugin.getUtils().getUtils().sendMessage(victim, deathMessage);
                        plugin.getKitPvP().getPlayers().getUser(victim.getUniqueId()).addDeaths();
                    }
                }
                return;
            }
            if (event.getDamager().getType() == EntityType.PLAYER) {
                Player player = (Player) event.getDamager();
                if ((victim.getHealth() - event.getFinalDamage()) <= 0) {
                    event.setCancelled(true);
                    victim.getInventory().clear();
                    victim.setHealth(20);
                    victim.setFoodLevel(20);
                    victim.getInventory().setBoots(null);
                    victim.getInventory().setHelmet(null);
                    victim.getInventory().setChestplate(null);
                    victim.getInventory().setLeggings(null);
                    plugin.getUtils().getUtils().sendMessage(victim, byPvPDeath.replace("%victim%",victim.getName()).replace("%attacker%",player.getName()));
                    plugin.getUtils().getUtils().sendMessage(player, byPvPKill.replace("%victim%",victim.getName()).replace("%attacker%",player.getName()));
                    plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()).addKills();
                    plugin.getKitPvP().getPlayers().getUser(victim.getUniqueId()).addDeaths();
                } else {
                    Bukkit.broadcastMessage("Player: " + victim.getName() + "  --->");
                    Bukkit.broadcastMessage("Health: " + victim.getHealth());
                    Bukkit.broadcastMessage("Health Scale: " + victim.getHealthScale());
                    Bukkit.broadcastMessage("Food: " + victim.getFoodLevel());
                }
            }
        }
    }


    public void update() {
        FileConfiguration messages = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.MESSAGES);
        byLavaDeath = messages.getString("messages.game.deathMessages.lava-death");
        if (byLavaDeath == null) byLavaDeath = "&7%victim% was on fire!";
        byVoidDeath = messages.getString("messages.game.deathMessages.void-death");
        if (byVoidDeath == null) byVoidDeath = "&7%victim% was searching a diamond.";
        byDefaultDeath = messages.getString("messages.game.deathMessages.otherCause-death");
        if (byDefaultDeath == null) byDefaultDeath = "&7%victim% died";
        byPvPDeath = messages.getString("messages.game.deathMessages.pvp-death");
        if (byPvPDeath == null) byDefaultDeath = "&7%victim% was killed by %attacker%";
        byBowDeath = messages.getString("messages.game.deathMessages.bow-death");
        if (byBowDeath == null) byBowDeath = "&7%victim% was shot by %attacker%";

        byLavaKill = messages.getString("messages.game.deathMessages.lava-kill");
        if (byLavaKill == null) byLavaKill = "&7%victim% was on fire!";
        byVoidKill = messages.getString("messages.game.deathMessages.void-kill");
        if (byVoidKill == null) byVoidKill = "&7%victim% was searching a diamond.";
        byDefaultKill = messages.getString("messages.game.deathMessages.otherCause-kill");
        if (byDefaultKill == null) byDefaultKill = "&7%victim% died";
        byPvPKill = messages.getString("messages.game.deathMessages.pvp-kill");
        if (byPvPKill == null) byDefaultKill = "&7%victim% was killed by %attacker%";
        byBowKill = messages.getString("messages.game.deathMessages.bow-kill");
        if (byBowKill == null) byBowKill = "&7%victim% was shot by %attacker%";
    }






    private String getDeathMessage(Player player, EntityDamageEvent.DamageCause cause) {
        switch (cause) {
            case FIRE:
            case FIRE_TICK:
            case LAVA:
                return byLavaDeath.replace("%victim%",player.getName());
            case DROWNING:
            case SUICIDE:
            case VOID:
                return byVoidDeath.replace("%victim%",player.getName());
            case ENTITY_ATTACK:
                return byPvPDeath.replace("%victim%",player.getName());
            default:
                return byDefaultDeath.replace("%victim%",player.getName());
        }
    }
}
