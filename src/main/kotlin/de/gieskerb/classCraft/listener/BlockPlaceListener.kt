package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.ClassCraft
import de.gieskerb.classCraft.classes.ClassSpecificLUT.MaterialPair
import de.gieskerb.classCraft.classes.ClassSpecificLUT.farmerBlocks
import de.gieskerb.classCraft.classes.ClassSpecificLUT.lumberjackBlocks
import de.gieskerb.classCraft.classes.ClassSpecificLUT.minerBlocks
import de.gieskerb.classCraft.classes.FarmerClass
import de.gieskerb.classCraft.data.PlayerData
import org.bukkit.block.data.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.metadata.FixedMetadataValue

class BlockPlaceListener : Listener {
    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val block = event.block
        val player = event.player
        val activeClass = PlayerData.getPlayerData(player.name)?.activeClass

        if (minerBlocks!!.contains(MaterialPair(block.type))
            || lumberjackBlocks!!.contains(MaterialPair(block.type))
            || farmerBlocks!!.contains(MaterialPair(block.type))
        ) {
            block.setMetadata("PLACED", FixedMetadataValue(ClassCraft.plugin, block))
        }

        if(activeClass is FarmerClass && activeClass.level >= 10) {
            if(farmerBlocks!!.contains(MaterialPair(block.type))&& block is Ageable) {
                block.age = block.maximumAge / 3
            }
        }
    }
}