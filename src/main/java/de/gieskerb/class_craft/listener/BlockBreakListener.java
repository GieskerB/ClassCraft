package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.classes.*;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getBlock();
        if(!block.getMetadata("PLACED").isEmpty()) {
            return;
        }

        BaseClass playerClass = PlayerData.getPlayerData(player.getName()).getActiveClass();


        switch (playerClass) {
            case MinerClass minerClass -> {
                if(ClassSpecificLUT.getMinerBlocks().contains(new ClassSpecificLUT.MaterialPair(block.getType()))) {
                    player.sendMessage("You have mined the right Block!");
                }
            }
            case LumberjackClass lumberjackClass -> {
                if(ClassSpecificLUT.getLumberjackBlocks().contains(new ClassSpecificLUT.MaterialPair(block.getType()))) {
                    player.sendMessage("You have chopped the right Block!");
                }
            }
            case FarmerClass farmerClass -> {
                if(ClassSpecificLUT.getFarmerBlocks().contains(new ClassSpecificLUT.MaterialPair(block.getType()))) {
                    player.sendMessage("You have farmed the right Block!");
                    // dont forget AGE
                }
            }
            default -> {
            }
        }
    }

}
