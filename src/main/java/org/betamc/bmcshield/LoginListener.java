package org.betamc.bmcshield;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

    @EventHandler(priority = Event.Priority.Lowest)
    public void onPlayerLogin(PlayerLoginEvent event) {
        for (String name : BMCShield.getKickList()) {
            if (name.equalsIgnoreCase(event.getPlayer().getName())) {
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER,
                    BMCShield.getConfig().getConfigString("settings.kick-message.value")
                );
            }
        }
    }
}
