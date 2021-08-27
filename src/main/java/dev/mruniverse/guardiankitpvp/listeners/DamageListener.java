package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;

public class DamageListener implements Listener {
    private final GuardianKitPvP plugin;

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
        byLavaDeath = messages.getString("messages.normal.deathMessages.lava-death");
        if (byLavaDeath == null) byLavaDeath = "&7%victim% was on fire!";
        byVoidDeath = messages.getString("messages.normal.deathMessages.void-death");
        if (byVoidDeath == null) byVoidDeath = "&7%victim% was searching a diamond.";
        byDefaultDeath = messages.getString("messages.normal.deathMessages.otherCause-death");
        if (byDefaultDeath == null) byDefaultDeath = "&7%victim% died";
        byPvPDeath = messages.getString("messages.normal.deathMessages.pvp-death");
        if (byPvPDeath == null) byDefaultDeath = "&7%victim% was killed by %attacker%";
        byBowDeath = messages.getString("messages.normal.deathMessages.bow-death");
        if (byBowDeath == null) byBowDeath = "&7%victim% was shot by %attacker%";

        byLavaKill = messages.getString("messages.normal.deathMessages.lava-kill");
        if (byLavaKill == null) byLavaKill = "&7%victim% was on fire!";
        byVoidKill = messages.getString("messages.normal.deathMessages.void-kill");
        if (byVoidKill == null) byVoidKill = "&7%victim% was searching a diamond.";
        byDefaultKill = messages.getString("messages.normal.deathMessages.otherCause-kill");
        if (byDefaultKill == null) byDefaultKill = "&7%victim% died";
        byPvPKill = messages.getString("messages.normal.deathMessages.pvp-kill");
        if (byPvPKill == null) byDefaultKill = "&7%victim% was killed by %attacker%";
        byBowKill = messages.getString("messages.normal.deathMessages.bow-kill");
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
            for(PotionEffect effect:player.getActivePotionEffects()){
                player.removePotionEffect(effect.getType());
            }
            plugin.getKitPvP().getListenerController().getNormalInventory().giveInventory(player,true);
            player.setFoodLevel(20);
            plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()).setLocationID("spawn");
            player.getInventory().setBoots(null);
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            String deathMessage;
            deathMessage = getDeathMessage(player, event.getCause());
            plugin.getUtils().getUtils().sendMessage(player, deathMessage);
            plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()).addDeaths();
            return;
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
            return;
        }
        if ((player.getHealth() - event.getFinalDamage()) <= 0 && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            event.setCancelled(true);
            Location MAP_LOCATION = plugin.getKitPvP().getListenerController().getMapLocation();
            if(MAP_LOCATION != null) {
                player.teleport(MAP_LOCATION);
            }
            for(PotionEffect effect:player.getActivePotionEffects()){
                player.removePotionEffect(effect.getType());
            }
            plugin.getKitPvP().getListenerController().getNormalInventory().giveInventory(player,true);
            player.setHealth(20);
            player.setFoodLevel(20);
            plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()).setLocationID("spawn");
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
                    Location MAP_LOCATION = plugin.getKitPvP().getListenerController().getMapLocation();
                    if(MAP_LOCATION != null) {
                        victim.teleport(MAP_LOCATION);
                    }

                    victim.setHealth(20);
                    for(PotionEffect effect:player.getActivePotionEffects()){
                        player.removePotionEffect(effect.getType());
                    }
                    plugin.getKitPvP().getListenerController().getNormalInventory().giveInventory(victim,true);
                    victim.setFoodLevel(20);
                    victim.getInventory().setBoots(null);
                    victim.getInventory().setHelmet(null);
                    victim.getInventory().setChestplate(null);
                    victim.getInventory().setLeggings(null);
                    String pvp = "&7Has muerto por " + player.getName();
                    String vt = "&aHas matado a " + victim.getName();
                    if(byPvPDeath != null) {
                        pvp = byPvPDeath.replace("%victim%", victim.getName()).replace("%attacker%", player.getName());
                    }
                    if(byPvPKill != null) {
                        vt = byPvPKill.replace("%victim%", victim.getName()).replace("%attacker%", player.getName());
                    }
                    plugin.getUtils().getUtils().sendMessage(victim, pvp);
                    plugin.getUtils().getUtils().sendMessage(player, vt);
                    plugin.getKitPvP().getPlayers().getUser(victim.getUniqueId()).setLocationID("spawn");
                    plugin.getKitPvP().getPlayers().getUser(player.getUniqueId()).addKills();
                    plugin.getKitPvP().getPlayers().getUser(victim.getUniqueId()).addDeaths();
                } else {
                    int health = (int)victim.getHealth() / 2;
                    health = health - (int)event.getFinalDamage();
                    String healthBar = "&7" + victim.getName() + " " + getHealth(health);
                    plugin.getUtils().getUtils().sendActionbar(player, ChatColor.translateAlternateColorCodes('&',healthBar));
                }
            }
        }
    }

    public String getHealth(int health) {
        if(health >= 10) {
            return "&4❤❤❤❤❤❤❤❤❤❤";
        }
        if(health >= 9) {
            return "&4❤❤❤❤❤❤❤❤❤&c❤";
        }
        if(health >= 8) {
            return "&4❤❤❤❤❤❤❤❤&c❤❤";
        }
        if(health >= 7) {
            return "&4❤❤❤❤❤❤❤&c❤❤&0❤";
        }
        if(health >= 6) {
            return "&4❤❤❤❤❤❤&c❤❤&0❤❤";
        }
        if(health >= 5) {
            return "&4❤❤❤❤❤&c❤❤&0❤❤❤";
        }
        if(health >= 4) {
            return "&4❤❤❤❤&c❤❤&0❤❤❤❤";
        }
        if(health >= 3) {
            return "&4❤❤❤&c❤❤&0❤❤❤❤❤";
        }
        if(health >= 2) {
            return "&4❤❤&c❤❤&0❤❤❤❤❤❤";
        }
        if(health >= 1) {
            return "&4❤&c❤❤&0❤❤❤❤❤❤❤";
        }
        return "&c❤❤&0❤❤❤❤❤❤❤❤";
    }


    public void update() {
        FileConfiguration messages = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.MESSAGES);
        byLavaDeath = messages.getString("messages.normal.deathMessages.lava-death");
        if (byLavaDeath == null) byLavaDeath = "&7%victim% was on fire!";
        byVoidDeath = messages.getString("messages.normal.deathMessages.void-death");
        if (byVoidDeath == null) byVoidDeath = "&7%victim% was searching a diamond.";
        byDefaultDeath = messages.getString("messages.normal.deathMessages.otherCause-death");
        if (byDefaultDeath == null) byDefaultDeath = "&7%victim% died";
        byPvPDeath = messages.getString("messages.normal.deathMessages.pvp-death");
        if (byPvPDeath == null) byDefaultDeath = "&7%victim% was killed by %attacker%";
        byBowDeath = messages.getString("messages.normal.deathMessages.bow-death");
        if (byBowDeath == null) byBowDeath = "&7%victim% was shot by %attacker%";

        byLavaKill = messages.getString("messages.normal.deathMessages.lava-kill");
        if (byLavaKill == null) byLavaKill = "&7%victim% was on fire!";
        byVoidKill = messages.getString("messages.normal.deathMessages.void-kill");
        if (byVoidKill == null) byVoidKill = "&7%victim% was searching a diamond.";
        byDefaultKill = messages.getString("messages.normal.deathMessages.otherCause-kill");
        if (byDefaultKill == null) byDefaultKill = "&7%victim% died";
        byPvPKill = messages.getString("messages.normal.deathMessages.pvp-kill");
        if (byPvPKill == null) byDefaultKill = "&7%victim% was killed by %attacker%";
        byBowKill = messages.getString("messages.normal.deathMessages.bow-kill");
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
