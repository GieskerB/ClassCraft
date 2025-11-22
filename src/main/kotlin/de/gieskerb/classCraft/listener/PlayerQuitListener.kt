package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.Main
import de.gieskerb.classCraft.data.PlayerData
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.io.File
import java.io.FileWriter
import java.io.IOException

/*
Black	     0		0x000000
Dark Blue	 1		0x0000AA
Dark Green	 2		0x00AA00
Dark Aqua	 3		0x00AAAA
Dark Red	 4		0xAA0000
Dark Purple	 5		0xAA00AA
Gold	     6		0xFFAA00
Gray	     7		0xAAAAAA
Dark Gray	 8		0x555555
Blue	     9		0x5555FF
Green	     a		0x55FF55
Aqua	     b		0x55FFFF
Red	         c		0xFF5555
Light Purple d		0xFF55FF
Yellow	     e		0xFFFF55
White	     f		0xFFFFFF
 */

class PlayerQuitListener : Listener {
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        val playerName = player.name

        val playerData = savePlayerData(playerName)

        if (playerData?.activeClass != null) {
//          "§eThe §6${playerData.activeClass!!.CLASS_NAME} §9$playerName§e left the game!"
            event.quitMessage(Component.text("The ", TextColor.color(0xFFFF55))
                .append(Component.text(playerData.activeClass!!.className, TextColor.color(0xFFAA00)))
                .append(Component.text(playerName, TextColor.color(0x5555FF)))
                .append(Component.text(" left the game!", TextColor.color(0xFFFF55)))
            )
            if (playerData.getDistanceTracker() != null) {
                playerData.getDistanceTracker()!!.despawnHorse()
            }
        } else {
            // "§9$playerName§e left the game!"
            event.quitMessage(Component.text(playerName, TextColor.color(0x5555FF))
                .append(Component.text(" left the game!", TextColor.color(0xFFFF55)))
            )
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
                        Bukkit.getServer().broadcast(Component.text("Could not create PlayerData File for Player $playerName",
                            TextColor.color(0xAA0000)))
                    }
                } catch (e: IOException) {
                    Bukkit.getServer().broadcast(Component.text("Could not create PlayerData File for Player $playerName",
                        TextColor.color(0xAA0000)))
                    println(e.message)
                    return null
                }
            }

            if (playerData == null && playerFile.exists()) {
                if (!playerFile.delete()) {
                    Bukkit.getServer().broadcast(Component.text("Could not delete PlayerData File for Player $playerName",
                        TextColor.color(0xAA0000)))
                }
            } else if (playerData != null) {
                try {
                    FileWriter(playerFile).use { file ->
                        file.write(PlayerData.getPlayerData(playerName)!!.toJSON().toString())
                    }
                } catch (e: IOException) {
                    Bukkit.getServer().broadcast(Component.text("Could not delete PlayerData File for Player $playerName",
                        TextColor.color(0xAA0000)))
                    println(e.message)
                }
            }
            return playerData
        }
    }
}