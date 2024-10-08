package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.commands.HorseCommand;
import org.bukkit.entity.Horse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryOpenListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() instanceof Horse horse) {
            if (!horse.getMetadata(HorseCommand.METADATA_IDENTIFIER).isEmpty()) {
                event.setCancelled(true);
            }
        }

    }

}
