package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.classes.BaseClass;
import de.gieskerb.class_craft.classes.ExplorerClass;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onStatisticIncrementEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        BaseClass playerClass = PlayerData.getPlayerData(player.getName()).getActiveClass();
        if (!(playerClass instanceof ExplorerClass explorerClass)) {
            return;
        }
        if ((int) event.getFrom().getX() != (int) event.getTo().getX() ||
                (int) event.getFrom().getZ() != (int) event.getTo().getZ()) {
            player.sendMessage((int) event.getFrom().getX() + " " + (int) event.getFrom().getY() + " -> " +
                                       (int) event.getTo().getZ() + " " + (int) event.getTo().getZ());
            explorerClass.addXp(1);
        }
    }

}
