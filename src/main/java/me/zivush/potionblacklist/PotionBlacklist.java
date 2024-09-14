package me.zivush.potionblacklist;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PotionBlacklist extends JavaPlugin {

    private List<UUID> blacklist;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        // Save default config if it doesn't exist
        saveDefaultConfig();
        config = getConfig();

        // Load blacklist from config
        blacklist = new ArrayList<>();
        List<String> blacklistStrings = config.getStringList("blacklist");
        for (String uuidString : blacklistStrings) {
            blacklist.add(UUID.fromString(uuidString));
        }

        // Register command executor
        getCommand("potion").setExecutor(new PotionBlacklistCommand(this));

        // Register event listener
        getServer().getPluginManager().registerEvents(new PotionBlacklistListener(this), this);

        getLogger().info("PotionBlacklist has been enabled!");
    }

    @Override
    public void onDisable() {
        // Save blacklist to config
        List<String> blacklistStrings = new ArrayList<>();
        for (UUID uuid : blacklist) {
            blacklistStrings.add(uuid.toString());
        }
        config.set("blacklist", blacklistStrings);
        saveConfig();

        getLogger().info("PotionBlacklist has been disabled!");
    }

    public List<UUID> getBlacklist() {
        return blacklist;
    }

    public String getMessage(String key) {
        String message = config.getString("messages." + key, "Message not found: " + key);
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
