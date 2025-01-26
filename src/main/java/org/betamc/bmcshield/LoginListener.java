package org.betamc.bmcshield;

import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.IOException;
import java.util.ArrayList;

public class LoginListener implements Listener {
    private BMCShield plugin;
    private Config config;
    private ArrayList<String> readerList;

    // Constructor to link the plugin instance
    public LoginListener(BMCShield plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.readerList = plugin.getReaderList();
    }

    @EventHandler(priority = Event.Priority.Normal)
    public void onPlayerLogin(PlayerLoginEvent event) throws IOException {
        for (int i = 0; i < readerList.size(); i++) {
            if (readerList.get(i).equalsIgnoreCase(event.getPlayer().getName())) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + config.getConfigString("settings.kick-message.value"));
            }
        }

    }
}
