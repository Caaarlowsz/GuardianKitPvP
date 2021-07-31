package dev.mruniverse.guardiankitpvp.listeners;

import dev.mruniverse.guardiankitpvp.GuardianKitPvP;
import dev.mruniverse.guardiankitpvp.enums.GuardianFiles;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final GuardianKitPvP plugin;

    private boolean isEnabled;

    private String format;

    public ChatListener(GuardianKitPvP plugin) {
        this.plugin = plugin;
        this.isEnabled = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getBoolean("settings.chat.toggle",true);
        this.format = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getString("settings.chat.format");
    }

    public void update() {
        this.isEnabled = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getBoolean("settings.chat.toggle",true);
        this.format = plugin.getKitPvP().getFileStorage().getControl(GuardianFiles.SETTINGS).getString("settings.chat.format");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) return;
        if(isEnabled) {
            String customFormat = format;
            for(Player player : Bukkit.getOnlinePlayers()) {
                plugin.getUtils().sendMessage(player,customFormat,event.getPlayer(),event.getMessage());
            }
            event.setCancelled(true);
        }
    }
}
