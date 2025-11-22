package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.classes.BaseClass
import de.gieskerb.classCraft.classes.ClassSpecificLUT.MaterialPair
import de.gieskerb.classCraft.classes.ClassSpecificLUT.farmerBlocks
import de.gieskerb.classCraft.classes.ClassSpecificLUT.lumberjackBlocks
import de.gieskerb.classCraft.classes.ClassSpecificLUT.minerBlocks
import de.gieskerb.classCraft.classes.FarmerClass
import de.gieskerb.classCraft.classes.LumberjackClass
import de.gieskerb.classCraft.classes.MinerClass
import de.gieskerb.classCraft.data.PlayerData
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
                    // TODO dont forget age
                }
            }
            else -> {}
        }
    }
}