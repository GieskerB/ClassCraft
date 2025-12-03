package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.Main
import de.gieskerb.classCraft.data.PlayerData
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.scheduler.BukkitRunnable

class PlayerItemConsumeListener : Listener {

    @EventHandler
    fun onItemConsume(event: PlayerItemConsumeEvent) {
        if (event.item.type == Material.MILK_BUCKET) {
            val playerData = PlayerData.getPlayerData(event.player.name)
            object : BukkitRunnable() {
                override fun run() {
//                    playerData?.activeClass?.reapplyRewardEffects()
                }
            }.runTaskLater(Main.plugin, 5L)
        }
    }
}