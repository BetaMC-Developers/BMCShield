package org.betamc.bmcshield;

import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.BufferedReader;
import java.io.IOException;

public class LoginListener implements Listener {
    private BMCShield plugin;
    private Config config;
    private BufferedReader fileReader;

    // Constructor to link the plugin instance
    public LoginListener(BMCShield plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.fileReader = plugin.getListReader();
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerLogin(PlayerLoginEvent event) throws IOException {
        String line;
        while ((line = fileReader.readLine()) != null) {
            if (line.equalsIgnoreCase(event.getPlayer().getName())) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + config.getConfigString("settings.kick-message.value"));
            }
        }

    }
}
