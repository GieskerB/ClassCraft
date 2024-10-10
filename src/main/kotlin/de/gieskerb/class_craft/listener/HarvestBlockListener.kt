package de.gieskerb.class_craft.listener

import de.gieskerb.class_craft.classes.BaseClass
import de.gieskerb.class_craft.classes.ClassSpecificLUT.MaterialPair
import de.gieskerb.class_craft.classes.ClassSpecificLUT.farmerBlocks
import de.gieskerb.class_craft.classes.FarmerClass
import de.gieskerb.class_craft.data.PlayerData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerHarvestBlockEvent

class HarvestBlockListener : Listener {
    @EventHandler
    fun onHarvest(event: PlayerHarvestBlockEvent) {
        val player = event.player
        val block = event.harvestedBlock
        val playerClass: BaseClass? = PlayerData.getPlayerData(player.name)?.activeClass

        if (playerClass is FarmerClass && farmerBlocks!!.contains(MaterialPair(block.type))) {
            player.sendMessage("You have harvested a crop!")
        }
    }
}