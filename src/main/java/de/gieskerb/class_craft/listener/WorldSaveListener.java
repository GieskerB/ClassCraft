package de.gieskerb.class_craft.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;
import org.jetbrains.annotations.NotNull;

public class WorldSaveListener implements Listener {

    private static String worldSaveName = null;

    @EventHandler
    public void onWorldSaveEvent(@NotNull WorldSaveEvent event) {
        if (worldSaveName == null) {
            worldSaveName = event.getWorld().getName();
        }
        if (event.getWorld().getName().equals(worldSaveName)) {
            // This will only be called once per save not 3 times(for each world once)
            PlayerQuitListener.saveAllPlayersData();
        }
    }

}
