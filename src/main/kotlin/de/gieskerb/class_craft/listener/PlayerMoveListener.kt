package de.gieskerb.class_craft.listener

import de.gieskerb.class_craft.classes.BaseClass
import de.gieskerb.class_craft.classes.ExplorerClass
import de.gieskerb.class_craft.data.PlayerData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class PlayerMoveListener : Listener {
    @EventHandler
    fun onStatisticIncrementEvent(event: PlayerMoveEvent) {
        val player = event.player
        val playerClass: BaseClass = PlayerData.getPlayerData(player.name)?.activeClass as? ExplorerClass
            ?: return
        if (event.from.x.toInt() != event.to!!.x.toInt() ||
            event.from.z.toInt() != event.to!!.z.toInt()
        ) {
            player.sendMessage(
                event.from.x.toInt().toString() + " " + event.from.y.toInt() + " -> " + event.to!!
                    .z.toInt() + " " + event.to!!.z.toInt()
            )
            playerClass.addXp(1)
        }
    }
}