package de.gieskerb.class_craft.listener

import de.gieskerb.class_craft.classes.BaseClass
import de.gieskerb.class_craft.classes.FarmerClass
import de.gieskerb.class_craft.data.PlayerData
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
        val player: Player = event.breeder as Player;
        val playerClass: BaseClass? = PlayerData.getPlayerData(player.name)?.activeClass
        if (playerClass is FarmerClass) {
            player.sendMessage("You have breed an animal!")
        }
    }
}