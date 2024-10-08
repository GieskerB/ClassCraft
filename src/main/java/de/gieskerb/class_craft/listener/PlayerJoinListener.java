package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.Main;
import de.gieskerb.class_craft.classes.BaseClass;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PlayerJoinListener implements Listener {

    public static void loadAllPlayersData() {
        var onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (Player onlinePlayer : onlinePlayers) {
            PlayerJoinListener.loadPlayerData(onlinePlayer.getName());
        }
    }

    public static PlayerData loadPlayerData(String playerName) {
        File playerFile = new File(
                Main.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "PlayerData" + File.separator +
                        playerName + ".json");

        PlayerData playerData;
        if (playerFile.exists()) {
            JSONParser parser = new JSONParser();
            try {
                JSONObject json = (JSONObject) parser.parse(new FileReader(playerFile));
                playerData = new PlayerData(json);
            } catch (IOException | ParseException e) {
                Bukkit.getServer().broadcastMessage(
                        "§4Could not load PlayerData for Player " + playerName + " due to " + e.getClass().getSimpleName());
                playerData = new PlayerData(playerName);
            }
        } else {
            playerData = new PlayerData(playerName);
        }

        PlayerData.addPlayerData(playerData);
        return playerData;
    }

    private void createFolder() {

        File folder = new File(Main.getPlugin().getDataFolder().getAbsolutePath());

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Bukkit.getServer().broadcastMessage("§4Could create folder for ClassCraft-Plugin");
            }
        }

        folder = new File(Main.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "PlayerData");

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Bukkit.getServer().broadcastMessage("§4Could create folder for PlayerData in ClassCraft-Plugin");
            }
        }
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {

        final Player player = event.getPlayer();
        final String playerName = player.getName();

        createFolder();
        PlayerData playerData = loadPlayerData(playerName);

        BaseClass playerClass = playerData.getActiveClass();
        if (playerClass != null) {
            event.setJoinMessage("§eThe §6" + playerClass.CLASS_NAME + " §9" + playerName + " §ejoined the game!");
            playerClass.reapplyRewardEffects();
        } else {
            event.setJoinMessage("§9" + playerName + " §ejoined the game!");
        }

    }
}
