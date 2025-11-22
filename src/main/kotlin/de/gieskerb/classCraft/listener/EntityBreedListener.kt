package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.classes.BaseClass
import de.gieskerb.classCraft.classes.FarmerClass
import de.gieskerb.classCraft.data.PlayerData
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityBreedEvent

class EntityBreedListener : Listener {
    @EventHandler
    fun onEntityBreed(event: EntityBreedEvent) {
        if (event.breeder !is Player) {
            return
        }
        val player: Player = event.breeder as Player
        val playerClass: BaseClass? = PlayerData.getPlayerData(player.name)?.activeClass
        if (playerClass is FarmerClass) {
            player.sendMessage("You have breed an animal!")
        }
    }
}