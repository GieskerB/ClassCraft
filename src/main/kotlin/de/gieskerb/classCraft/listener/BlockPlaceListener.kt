package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.Main
import de.gieskerb.classCraft.classes.ClassSpecificLUT.MaterialPair
import de.gieskerb.classCraft.classes.ClassSpecificLUT.farmerBlocks
import de.gieskerb.classCraft.classes.ClassSpecificLUT.lumberjackBlocks
import de.gieskerb.classCraft.classes.ClassSpecificLUT.minerBlocks
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.metadata.FixedMetadataValue

class BlockPlaceListener : Listener {
    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val block = event.block

        if (minerBlocks!!.contains(MaterialPair(block.type))
            || lumberjackBlocks!!.contains(MaterialPair(block.type))
            || farmerBlocks!!.contains(MaterialPair(block.type))
        ) {
            block.setMetadata("PLACED", FixedMetadataValue(Main.plugin, block))
        }
    }
}