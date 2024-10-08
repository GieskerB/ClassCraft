package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.Main;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnListener implements Listener {
    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerData.getPlayerData(player.getName());

        player.sendMessage("You respawned");
        if (playerData.getActiveClass() != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    playerData.getActiveClass().reapplyRewardEffects();
                }
            }.runTaskLater(Main.getPlugin(), 5L);
        }
    }
}
