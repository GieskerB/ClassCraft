package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.Main;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerQuitListener implements Listener {

    public static void saveAllPlayersData() {
        var onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (Player onlinePlayer : onlinePlayers) {
            PlayerData playerData = savePlayerData(onlinePlayer.getName());
            if (playerData != null && playerData.getDistanceTracker() != null) {
                PlayerData.getPlayerData(onlinePlayer.getName()).getDistanceTracker().despawnHorse();
            }
        }
    }

    public static PlayerData savePlayerData(String playerName) {

        File playerFile = new File(
                Main.getPlugin().getDataFolder() + File.separator + "PlayerData" + File.separator + playerName +
                        ".json");
        PlayerData playerData = PlayerData.getPlayerData(playerName);
        if (!playerFile.exists()) {
            try {
                if (!playerFile.createNewFile()) {
                    Bukkit.getServer().broadcastMessage("§4Could create  PlayerData File for Player " + playerName);
                }
            } catch (IOException e) {
                Bukkit.getServer().broadcastMessage("§4Could not create PlayerData File for Player " + playerName);
                Bukkit.broadcastMessage("§4" + e.getMessage());
                return null;
            }
        }

        if (playerData == null && playerFile.exists()) {
            if (!playerFile.delete()) {
                Bukkit.broadcastMessage("§4Could not delete PlayerData for Player " + playerName);
            }
        } else if (playerData != null) {
            try (FileWriter file = new FileWriter(playerFile)) {
                file.write(PlayerData.getPlayerData(playerName).toJSON().toString());
            } catch (IOException e) {
                Bukkit.broadcastMessage("§4Could not write PlayerData for Player " + playerName);
                Bukkit.broadcastMessage("§4" + e.getMessage());
            }
        }
        return playerData;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        final Player player = event.getPlayer();
        final String playerName = player.getName();

        PlayerData playerData = PlayerQuitListener.savePlayerData(playerName);

        if (playerData != null && playerData.getActiveClass() != null) {
            event.setQuitMessage(
                    "§eThe §6" + playerData.getActiveClass().CLASS_NAME + " §9" + playerName + " §eleft the game!");
            if (playerData.getDistanceTracker() != null) {
                playerData.getDistanceTracker().despawnHorse();
            }
        } else {
            event.setQuitMessage("§9" + playerName + " §eleft the game!");
        }
        PlayerData.removePlayerData(playerName);
    }
}
