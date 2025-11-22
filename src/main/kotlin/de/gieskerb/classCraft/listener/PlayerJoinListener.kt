package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.Main
import de.gieskerb.classCraft.data.PlayerData
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import java.io.File

class PlayerJoinListener : Listener {
    private fun createFolder() {
        var folder = File(Main.plugin.dataFolder.absolutePath)

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Bukkit.getServer().broadcast(Component.text("Could not create Folder for ClassCraft-Plugin",
                    TextColor.color(0xAA0000)))
            }
        }

        folder = File(Main.plugin.dataFolder.absolutePath + File.separator + "PlayerData")

        if (!folder.exists()) {
            if (!folder.mkdir()) {
                Bukkit.getServer().broadcast(Component.text("Could not create PlayerData Folder for ClassCraft-Plugin",
                    TextColor.color(0xAA0000)))
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
            // "§eThe §6" + playerClass.CLASS_NAME + " §9" + playerName + "§e joined the game!"
            event.joinMessage(Component.text("The ", TextColor.color(0xFFFF55))
                .append(Component.text(playerData.activeClass!!.className, TextColor.color(0xFFAA00)))
                .append(Component.text(playerName, TextColor.color(0x5555FF)))
                .append(Component.text(" joined the game!", TextColor.color(0xFFFF55)))
            )
            playerClass.reapplyRewardEffects()
        } else {
            // "§9$playerName§e joined the game!"
            event.joinMessage(Component.text(playerName, TextColor.color(0x5555FF))
                .append(Component.text(" joined the game!", TextColor.color(0xFFFF55)))
            )
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