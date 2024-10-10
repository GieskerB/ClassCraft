package de.gieskerb.class_craft.listener

import de.gieskerb.class_craft.Main
import de.gieskerb.class_craft.data.PlayerData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.scheduler.BukkitRunnable

class RespawnListener : Listener {
    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        val playerData = PlayerData.getPlayerData(player.name)

        if (playerData!!.activeClass != null) {
            object : BukkitRunnable() {
                override fun run() {
                    playerData.activeClass!!.reapplyRewardEffects()
                }
            }.runTaskLater(Main.plugin, 5L)
        }
    }
}