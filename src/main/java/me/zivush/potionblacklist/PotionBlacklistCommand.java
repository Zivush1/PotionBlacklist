package me.zivush.potionblacklist;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PotionBlacklistCommand implements CommandExecutor, TabCompleter {

    private final PotionBlacklist plugin;

    public PotionBlacklistCommand(PotionBlacklist plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender has the required permission
        if (!sender.hasPermission("potion.blacklist")) {
            sender.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        // Validate command arguments
        if (args.length != 2 || !args[0].equalsIgnoreCase("blacklist")) {
            sender.sendMessage(plugin.getMessage("usage"));
            return true;
        }

        String playerName = args[1];
        Player target = Bukkit.getPlayer(playerName);

        // Check if the target player is online
        if (target == null) {
            sender.sendMessage(plugin.getMessage("player-not-found").replace("%player%", playerName));
            return true;
        }

        UUID targetUUID = target.getUniqueId();
        List<UUID> blacklist = plugin.getBlacklist();

        // Toggle the player's blacklist status
        if (blacklist.contains(targetUUID)) {
            blacklist.remove(targetUUID);
            sender.sendMessage(plugin.getMessage("removed-from-blacklist").replace("%player%", playerName));
        } else {
            blacklist.add(targetUUID);
            sender.sendMessage(plugin.getMessage("added-to-blacklist").replace("%player%", playerName));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        // Provide tab completion for subcommands and player names
        if (args.length == 1) {
            completions.add("blacklist");
        } else if (args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                completions.add(player.getName());
            }
        }

        return completions;
    }
}