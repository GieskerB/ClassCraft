package de.gieskerb.class_craft.listener;

import de.gieskerb.class_craft.classes.FarmerClass;
import de.gieskerb.class_craft.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.HashMap;
import java.util.Map;

public class HungerChangeListener implements Listener {

    private static Map<String, Integer> lastHungerValue = new HashMap<>();

    private static int getLastHunger(String playerName) {
        if(lastHungerValue.containsKey(playerName)) {
            return lastHungerValue.get(playerName);
        }
        return 20;
    }

    public static void  removePlayer(String playerName) {
        lastHungerValue.remove(playerName);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
       final Player player = (Player) e.getEntity();
       final PlayerData playerData = PlayerData.getPlayerData(player.getName());

       if (playerData.getActiveClass() != null && playerData.getActiveClass() instanceof FarmerClass farmerClass && farmerClass.getLevel() >=15) {
           int newFoodLevel = e.getFoodLevel();
           if(newFoodLevel < getLastHunger(player.getName())) {
               e.setFoodLevel(e.getFoodLevel()-1);
           }
           lastHungerValue.put(player.getName(), e.getFoodLevel());
            player.sendMessage(e.getFoodLevel() + "");
       }
    }
}
