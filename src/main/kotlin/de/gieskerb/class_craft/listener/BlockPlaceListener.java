package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.Main;
import de.gieskerb.class_craft.classes.ClassSpecificLUT;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final Block block = event.getBlock();

        if (ClassSpecificLUT.getMinerBlocks().contains(new ClassSpecificLUT.MaterialPair(block.getType()))
                || ClassSpecificLUT.getLumberjackBlocks().contains(new ClassSpecificLUT.MaterialPair(block.getType()))
                || ClassSpecificLUT.getFarmerBlocks().contains(new ClassSpecificLUT.MaterialPair(block.getType()))) {

            block.setMetadata("PLACED", new FixedMetadataValue(Main.getPlugin(), block));
        }


    }

}
