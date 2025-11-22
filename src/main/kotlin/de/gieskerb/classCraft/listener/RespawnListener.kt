package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.Main
import de.gieskerb.classCraft.data.PlayerData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.scheduler.BukkitRunnable

class RespawnListener : Listener {
    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        val playerData = PlayerData.getPlayerData(event.player.name)
        object : BukkitRunnable() {
            override fun run() {
                playerData?.activeClass?.reapplyRewardEffects()
            }
        }.runTaskLater(Main.plugin, 5L)
    }
}