package de.gieskerb.class_craft.listener

import de.gieskerb.class_craft.classes.BaseClass
import de.gieskerb.class_craft.classes.ClassSpecificLUT.MaterialPair
import de.gieskerb.class_craft.classes.ClassSpecificLUT.farmerBlocks
import de.gieskerb.class_craft.classes.ClassSpecificLUT.lumberjackBlocks
import de.gieskerb.class_craft.classes.ClassSpecificLUT.minerBlocks
import de.gieskerb.class_craft.classes.FarmerClass
import de.gieskerb.class_craft.classes.LumberjackClass
import de.gieskerb.class_craft.classes.MinerClass
import de.gieskerb.class_craft.data.PlayerData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener : Listener {
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val block = event.block
        if (block.getMetadata("PLACED").isNotEmpty()) {
            return
        }

        val playerClass: BaseClass? = PlayerData.getPlayerData(player.name)?.activeClass


        when (playerClass) {
            is MinerClass -> {
                if (minerBlocks!!.contains(MaterialPair(block.type))) {
                    player.sendMessage("You have mined the right Block!")
                }
            }

            is LumberjackClass -> {
                if (lumberjackBlocks!!.contains(MaterialPair(block.type))) {
                    player.sendMessage("You have chopped the right Block!")
                }
            }

            is FarmerClass -> {
                if (farmerBlocks!!.contains(MaterialPair(block.type))) {
                    player.sendMessage("You have farmed the right Block!")
                    // dont forget AGE
                }
            }

            else -> {}
        }
    }
}