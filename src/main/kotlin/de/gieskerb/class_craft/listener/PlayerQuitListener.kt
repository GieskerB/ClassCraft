package de.gieskerb.class_craft.listener

import de.gieskerb.class_craft.Main
import de.gieskerb.class_craft.data.PlayerData
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File
import java.io.FileWriter
import java.io.IOException

class PlayerQuitListener : Listener {
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        val playerName = player.name

        val playerData = savePlayerData(playerName)

        if (playerData?.activeClass != null) {
            event.quitMessage = "§eThe §6${playerData.activeClass!!.CLASS_NAME} §9$playerName §eleft the game!"
            if (playerData.getDistanceTracker() != null) {
                playerData.getDistanceTracker()!!.despawnHorse()
            }
        } else {
            event.quitMessage = "§9$playerName §eleft the game!"
        }
        PlayerData.removePlayerData(playerName)
    }

    companion object {
        fun saveAllPlayersData() {
            val onlinePlayers = Bukkit.getServer().onlinePlayers
            for (onlinePlayer in onlinePlayers) {
                val playerData = savePlayerData(onlinePlayer.name)
                if (playerData?.getDistanceTracker() != null) {
                    PlayerData.getPlayerData(onlinePlayer.name)!!.getDistanceTracker()!!.despawnHorse()
                }
            }
        }

        fun savePlayerData(playerName: String): PlayerData? {
            val playerFile = File(
                Main.plugin.dataFolder.toString() + File.separator + "PlayerData" + File.separator + playerName + ".json"
            )
            val playerData = PlayerData.getPlayerData(playerName)
            if (!playerFile.exists()) {
                try {
                    if (!playerFile.createNewFile()) {
                        Bukkit.getServer().broadcastMessage("§4Could create  PlayerData File for Player $playerName")
                    }
                } catch (e: IOException) {
                    Bukkit.getServer().broadcastMessage("§4Could not create PlayerData File for Player $playerName")
                    Bukkit.broadcastMessage("§4" + e.message)
                    return null
                }
            }

            if (playerData == null && playerFile.exists()) {
                if (!playerFile.delete()) {
                    Bukkit.broadcastMessage("§4Could not delete PlayerData for Player $playerName")
                }
            } else if (playerData != null) {
                try {
                    FileWriter(playerFile).use { file ->
                        file.write(PlayerData.getPlayerData(playerName)!!.toJSON().toString())
                    }
                } catch (e: IOException) {
                    Bukkit.broadcastMessage("§4Could not write PlayerData for Player $playerName")
                    Bukkit.broadcastMessage("§4" + e.message)
                }
            }
            return playerData
        }
    }
}