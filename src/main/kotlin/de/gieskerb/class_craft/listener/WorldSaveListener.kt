package de.gieskerb.class_craft.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldSaveEvent

class WorldSaveListener : Listener {
    @EventHandler
    fun onWorldSaveEvent(event: WorldSaveEvent) {
        if (worldSaveName == null) {
            worldSaveName = event.world.name
        }
        if (event.world.name == worldSaveName) {
            // This will only be called once per save not 3 times(for each world once)
            PlayerQuitListener.saveAllPlayersData()
        }
    }

    companion object {
        private var worldSaveName: String? = null
    }
}