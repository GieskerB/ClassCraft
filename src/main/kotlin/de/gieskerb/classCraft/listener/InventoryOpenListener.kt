package de.gieskerb.classCraft.listener

import de.gieskerb.classCraft.commands.HorseCommand
import org.bukkit.entity.Horse
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent

class InventoryOpenListener : Listener {
    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        if (event.inventory.holder is Horse) {
            val horse:Horse = event.inventory.holder as Horse
            if (horse.getMetadata(HorseCommand.METADATA_IDENTIFIER).isNotEmpty()) {
                event.isCancelled = true
            }
        }
    }
}