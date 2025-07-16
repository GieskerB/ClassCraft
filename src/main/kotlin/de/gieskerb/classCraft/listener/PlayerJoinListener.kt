package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.ClassCraft
import de.gieskerb.classCraft.data.PlayerData
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.bukkit.entity.Player
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class PlayerJoinListener : Listener {
    private fun createFolder() {
        var folder = File(ClassCraft.plugin.dataFolder.absolutePath)

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Bukkit.getServer().broadcastMessage("§4Could create folder for ClassCraft-Plugin")
            }
        }

        folder = File(ClassCraft.plugin.dataFolder.absolutePath + File.separator + "PlayerData")

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Bukkit.getServer().broadcastMessage("§4Could create folder for PlayerData in ClassCraft-Plugin")
            }
        }
    }

    @EventHandler
    fun onLogin(event: PlayerJoinEvent) {
        val player = event.player
        val playerName = player.name

        createFolder()
        val playerData = loadPlayerData(playerName)

        val playerClass = playerData.activeClass
        if (playerClass != null) {
            event.joinMessage = "§eThe §6" + playerClass.className + " §9" + playerName + " §ejoined the game!"
            playerClass.reapplyRewardEffects()
        } else {
            event.joinMessage = "§9$playerName §ejoined the game!"
        }
    }

    companion object {
        fun loadAllPlayersData() {
            val onlinePlayers = Bukkit.getServer().onlinePlayers
            for (onlinePlayer in onlinePlayers) {
                loadPlayerData(onlinePlayer.name)
            }
        }

        fun loadPlayerData(playerName: String): PlayerData {
            val playerFile = File(ClassCraft.plugin.dataFolder.absolutePath + File.separator + "PlayerData" + File.separator +
                    playerName + ".json")

            var playerData: PlayerData
            if (playerFile.exists()) {
                try {
                    val jsonString = Files.readString(Paths.get(playerFile.toURI()))
                    val loadedData = PlayerData.Companion.deserialize(jsonString)
                    playerData = loadedData
                } catch (e: IOException) {
                    Bukkit.getServer().broadcastMessage(
                        "§4Could not load PlayerData for Player " + playerName + " due to " + e.javaClass.simpleName
                    )
                    e.printStackTrace()
                    playerData = PlayerData(playerName)
                } catch (e: SerializationException) {
                    Bukkit.getServer().broadcastMessage(
                        "§4Could not parse PlayerData for Player " + playerName + " due to " + e.javaClass.simpleName + ". Data might be corrupted."
                    )
                    e.printStackTrace()
                    playerData = PlayerData(playerName)
                }
            } else {
                playerData = PlayerData(playerName)
            }

            PlayerData.removePlayerData(playerName)
            PlayerData.addPlayerData(playerData)
            return playerData
        }
    }
}