package org.betamc.bmcshield.commands;

import org.betamc.bmcshield.BMCShield;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class BMCShieldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String addSyntax = "§c/bmcshield add <player>";
        String removeSyntax = "§c/bmcshield remove <player>";
        String reloadSyntax = "§c/bmcshield reload";

        if (args.length < 1) {
            sender.sendMessage("§cInvalid syntax.");
            sender.sendMessage(addSyntax);
            sender.sendMessage(removeSyntax);
            sender.sendMessage(reloadSyntax);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "add":
                if (args.length < 2) {
                    sender.sendMessage("§cInvalid syntax.");
                    sender.sendMessage(addSyntax);
                    return true;
                }
                addToList(sender, args[1]);
                break;
            case "remove":
                if (args.length < 2) {
                    sender.sendMessage("§cInvalid syntax.");
                    sender.sendMessage(removeSyntax);
                    return true;
                }
                removeFromList(sender, args[1]);
                break;
            case "reload":
                reloadList(sender);
                break;
            default:
                sender.sendMessage("§cInvalid syntax.");
                sender.sendMessage(addSyntax);
                sender.sendMessage(removeSyntax);
                sender.sendMessage(reloadSyntax);
        }

        return true;
    }

    private void addToList(CommandSender sender, String name) {
        name = name.toLowerCase();
        Pattern namePattern = Pattern.compile("^[a-z0-9_]{3,16}$");
        if (!namePattern.matcher(name).matches()) {
            sender.sendMessage("§cThe username " + name + " is invalid.");
            return;
        }

        BMCShield.getKickList().add(name);
        BMCShield.saveKickList();
        sender.sendMessage("§aAdded " + name + " to the kick list.");
        Player player = Bukkit.getPlayer(name);
        if (player != null) {
            player.kickPlayer(BMCShield.getConfig().getConfigString("settings.kick-message.value"));
            sender.sendMessage("§7" + player.getName() + " was auto-kicked as they were online.");
        }
    }

    private void removeFromList(CommandSender sender, String name) {
        name = name.toLowerCase();
        boolean removed = BMCShield.getKickList().remove(name);
        if (removed) {
            BMCShield.saveKickList();
            sender.sendMessage("§aRemoved " + name + " from the kick list.");
        } else {
            sender.sendMessage("§cCould not find " + name + " in the kick list.");
        }
    }

    private void reloadList(CommandSender sender) {
        BMCShield.saveKickList();
        BMCShield.loadKickList();
        sender.sendMessage("§aReloaded the kick list.");
    }
}
