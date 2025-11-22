package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.Main
import de.gieskerb.classCraft.data.PlayerData
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File
import java.io.FileReader
import java.io.IOException

class PlayerJoinListener : Listener {
    private fun createFolder() {
        var folder = File(Main.plugin.dataFolder.absolutePath)

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Bukkit.getServer().broadcastMessage("§4Could create folder for ClassCraft-Plugin")
            }
        }

        folder = File(Main.plugin.dataFolder.absolutePath + File.separator + "PlayerData")

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
            event.joinMessage = "§eThe §6" + playerClass.CLASS_NAME + " §9" + playerName + " §ejoined the game!"
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
            val playerFile = File(
                Main.plugin.dataFolder.absolutePath + File.separator + "PlayerData" + File.separator +
                        playerName + ".json"
            )

            var playerData: PlayerData
            if (playerFile.exists()) {
                // TODO
//                val parser = JSONParser()
//                try {
//                    val json: JSONObject = parser.parse(FileReader(playerFile)) as JSONObject
//                    playerData = PlayerData(json)
//                } catch (e: IOException) {
//                    Bukkit.getServer().broadcastMessage(
//                        "§4Could not load PlayerData for Player " + playerName + " due to " + e.javaClass.simpleName
//                    )
//                    playerData = PlayerData(playerName)
//                } catch (e: ParseException) {
//                    Bukkit.getServer().broadcastMessage(
//                        "§4Could not load PlayerData for Player " + playerName + " due to " + e.javaClass.getSimpleName()
//                    )
                    playerData = PlayerData(playerName)
//                }
            } else {
                playerData = PlayerData(playerName)
            }

            PlayerData.addPlayerData(playerData)
            return playerData
        }
    }
}