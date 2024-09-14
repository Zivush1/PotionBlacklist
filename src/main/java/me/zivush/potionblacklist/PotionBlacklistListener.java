package me.zivush.potionblacklist;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class PotionBlacklistListener implements Listener {

    private final PotionBlacklist plugin;

    public PotionBlacklistListener(PotionBlacklist plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPotionConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        // Check if the consumed item is a potion
        if (item.getItemMeta() instanceof PotionMeta) {
            // Prevent blacklisted players from using potions
            if (plugin.getBlacklist().contains(player.getUniqueId())) {
                event.setCancelled(true);
                player.sendMessage(plugin.getMessage("potion-blocked"));
            }
        }
    }

    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent event) {
        // Check if the entity is a player and the cause is from a potion
        if (event.getEntity() instanceof Player &&
                (event.getCause() == EntityPotionEffectEvent.Cause.POTION_SPLASH ||
                        event.getCause() == EntityPotionEffectEvent.Cause.POTION_DRINK ||
                        event.getCause() == EntityPotionEffectEvent.Cause.AREA_EFFECT_CLOUD)) {

            Player player = (Player) event.getEntity();

            // Prevent blacklisted players from receiving potion effects
            if (plugin.getBlacklist().contains(player.getUniqueId())) {
                event.setCancelled(true);
                player.sendMessage(plugin.getMessage("potion-blocked"));
            }
        }
    }
}

