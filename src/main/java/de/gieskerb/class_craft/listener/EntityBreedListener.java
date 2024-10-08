package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.classes.BaseClass;
import de.gieskerb.class_craft.classes.FarmerClass;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class EntityBreedListener implements Listener {

    @EventHandler
    public void onEntityBreed(EntityBreedEvent event) {
        if (!(event.getBreeder() instanceof Player player)) {
            return;
        }

        BaseClass playerClass = PlayerData.getPlayerData(player.getName()).getActiveClass();
        if (playerClass instanceof FarmerClass farmerClass) {
            player.sendMessage("You have breed an animal!");
        }
    }

}
