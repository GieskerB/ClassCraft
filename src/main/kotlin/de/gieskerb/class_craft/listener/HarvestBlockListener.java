package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.classes.BaseClass;
import de.gieskerb.class_craft.classes.ClassSpecificLUT;
import de.gieskerb.class_craft.classes.FarmerClass;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

public class HarvestBlockListener implements Listener {

    @EventHandler
    public void onHarvest(PlayerHarvestBlockEvent event) {

        final Player player = event.getPlayer();
        final Block block = event.getHarvestedBlock();
        BaseClass playerClass = PlayerData.getPlayerData(player.getName()).getActiveClass();

        if (playerClass instanceof FarmerClass farmerClass &&
                ClassSpecificLUT.getFarmerBlocks().contains(new ClassSpecificLUT.MaterialPair(block.getType()))) {
            player.sendMessage("You have harvested a crop!");
        }
    }

}
