package org.betamc.bmcshield.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.betamc.bmcshield.Config;
import org.betamc.bmcshield.BMCShield;

import java.util.Objects;

public class BMCShieldCommand implements CommandExecutor {

    private final BMCShield plugin;

    private final Config config;

    public BMCShieldCommand(BMCShield plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        if (!sender.hasPermission("bmcshield.bmcshield") && !sender.isOp()) {
            sender.sendMessage("You do not have permission to execute this command.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("Not enough arguments.");
            return true;
        }
        
        /* TODO
        String playerName;

        switch (args[1].toLowerCase()) {
            case "add":
                playerName = args[2];
                sender.sendMessage(ChatColor.GREEN + "Added " + playerName + "to kick list.");
                break;
            case "remove":
                playerName = args[2];
                sender.sendMessage(ChatColor.GREEN + "Removed " + playerName + "from kick list.");
                break;
            case "reloadlist":
                sender.sendMessage(ChatColor.GREEN + "Reloaded player list.");
                break;
            case "reloadconfig":
                sender.sendMessage(ChatColor.GREEN + "Reloaded config file.");
                break;
        }
         */


        return true;
    }
}
