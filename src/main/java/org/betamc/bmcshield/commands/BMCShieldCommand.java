package org.betamc.bmcshield.commands;

import org.betamc.bmcshield.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.betamc.bmcshield.BMCShield;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BMCShieldCommand implements CommandExecutor {

    private final File file;
    private Config config;

    public BMCShieldCommand(BMCShield plugin) {
        this.file = plugin.getListFile();
        this.config = plugin.getConfig();
    }

    private void reloadConfigFile() {
        BMCShield bmcClass = new BMCShield();
        bmcClass.loadListFile();
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

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Not enough arguments.");
            sender.sendMessage(ChatColor.RED + "Syntax: /bmcshield <add/remove/reloadlist/reloadconfig> [player]");
            return true;
        }

        String playerName;

        switch (args[0].toLowerCase()) {
            case "add":
                playerName = args[1];

                if (playerName.length() <= 16) {
                    try {
                        FileWriter fw = new FileWriter(file, true);
                        fw.write("\n" + playerName);
                        fw.close();
                        sender.sendMessage(ChatColor.GREEN + "Added " + playerName + " to the kick list.");
                        BMCShield bmcClass = new BMCShield();
                        bmcClass.loadListFile();
                        Player player = Bukkit.getServer().getPlayer(playerName);
                        if (player != null) {
                            player.kickPlayer(ChatColor.RED + config.getConfigString("settings.kick-message.value"));
                            sender.sendMessage(ChatColor.GREEN + "Player was auto-kicked as they were online.");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    sender.sendMessage(ChatColor.RED + "The username " + playerName + " is too long (max username length is 16 characters).");
                }

                reloadConfigFile();
                sender.sendMessage(ChatColor.GREEN + "Reloaded player list.");
                break;
            case "remove":
                playerName = args[1];
                sender.sendMessage(ChatColor.RED + "Not implemented yet. Please delete the entry in the list.txt file and run /bmcshield reloadlist");
                break;
            case "reloadlist":
                reloadConfigFile();
                sender.sendMessage(ChatColor.GREEN + "Reloaded player list.");
                break;
            default:
                sender.sendMessage(ChatColor.RED + "Invalid argument.");
                sender.sendMessage(ChatColor.RED + "Syntax: /bmcshield <add/remove/reloadlist> [player]");
                return false;
        }

        return true;
    }
}
